package com.xmap_api.dao;

import com.xmap_api.dao.mappers.DefaultSpotRowMapper;
import com.xmap_api.dto.request.NewSpotDTO;
import com.xmap_api.dto.response.DefaultSpotDTO;
import com.xmap_api.dto.response.SpotInfoForMapDTO;
import com.xmap_api.dto.response.SpotInfoForMapDialogDTO;
import com.xmap_api.dto.thymeleaf_model.MinSpot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class SpotDAO {
    private final JdbcTemplate jdbcTemplate;
    private final JdbcClient jdbcClient;
    private final DefaultSpotRowMapper defaultSpotRowMapper = new DefaultSpotRowMapper();

    public SpotDAO(JdbcTemplate jdbcTemplate, JdbcClient jdbcClient) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcClient = jdbcClient;
    }

    public Page<DefaultSpotDTO> findAllDefaultSpots(Pageable pageable) {
        List<DefaultSpotDTO> content = jdbcClient.sql("""
            SELECT id, name, lat, lon, inserted_at, updated_at, description,
                   c.name AS "city"
              FROM spot s
              JOIN city c ON c.id = s.city_id
          ORDER BY inserted_at DESC
             LIMIT :limit
            OFFSET :offset
        """)
                .params(Map.of(
                    "limit", pageable.getPageSize(),
                    "offset", pageable.getOffset()))
                .query(defaultSpotRowMapper).list();
        return new PageImpl<>(content, pageable, countTotalElements());
    }

    public Page<MinSpot> getWithFirstImage(Pageable pageable, String fileLinkTemplate, String fileLinkPathParam) {
        List<MinSpot> content = jdbcClient.sql("""
              WITH spot_with_ranked_images AS (
                  SELECT s.id, s.name, s.lat, s.lon, s.inserted_at, s.updated_at, s.description,
                         sf.id AS file_id,
                         ROW_NUMBER() OVER(PARTITION BY s.id ORDER BY sf.uploaded_at) AS rn
                    FROM spot s
                    JOIN spot_s3_file ssf ON ssf.spot_id = id
                    JOIN s3_file sf ON sf.id = ssf.s3_file_id
              )
            SELECT id, name, lat, lon, inserted_at, updated_at, description, file_id
              FROM spot_with_ranked_images
             WHERE rn = 1
          ORDER BY inserted_at DESC
             LIMIT :limit
            OFFSET :offset
        """)
                .params(Map.of(
                        "limit", pageable.getPageSize(),
                        "offset", pageable.getOffset()))
                .query((rs, rowNum) -> new MinSpot(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getDouble("lat"),
                        rs.getDouble("lon"),
                        new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp("inserted_at")),
                        new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp("updated_at")),
                        rs.getString("description"),
                        fileLinkTemplate.replace(fileLinkPathParam, rs.getString("file_id"))
                ))
                .list();
        return new PageImpl<>(content, pageable, countTotalElements());
    }

    public SpotInfoForMapDialogDTO getWithFirstImage(UUID spotId, String fileLinkTemplate, String fileLinkPathParam) {
        return jdbcClient.sql("""
              WITH spot_with_ranked_images AS (
                  SELECT s.name,
                         sf.id AS file_id,
                         ROW_NUMBER() OVER(PARTITION BY s.id ORDER BY sf.uploaded_at) AS rn
                    FROM spot s
                    JOIN spot_s3_file ssf ON ssf.spot_id = id
                    JOIN s3_file sf ON sf.id = ssf.s3_file_id
                   WHERE s.id = :spotId
              )
            SELECT name, file_id
              FROM spot_with_ranked_images
             WHERE rn = 1
        """)
                .param("spotId", spotId)
                .query((rs, rowNum) -> new SpotInfoForMapDialogDTO(
                        rs.getString("name"),
                        fileLinkTemplate.replace(fileLinkPathParam, rs.getString("file_id"))
                ))
                .single();
    }

    public List<SpotInfoForMapDTO> getMinSpotInfoForMap() {
        return jdbcClient.sql("""
                   SELECT id, lon, lat FROM spot
               """)
                .query((rs, rowNum) -> new SpotInfoForMapDTO(
                        rs.getString("id"),
                        rs.getDouble("lon"),
                        rs.getDouble("lat")
                ))
                .list();
    }

    public DefaultSpotDTO findById(UUID spotId) {
        return jdbcTemplate.query("""
                  SELECT s.id, s.name, s.lat, s.lon, s.inserted_at, s.updated_at, s.description,
                         c.name AS "city"
                    FROM spot s
                    JOIN city c ON c.id = s.city_id
                   WHERE s.id = ?
              """, defaultSpotRowMapper, spotId).getFirst();
    }

    public UUID addNewSpot(NewSpotDTO spot) {
        return jdbcTemplate.queryForObject("""
                  INSERT INTO spot (name, lat, lon, description)
                       VALUES (?, ?, ?, ?)
                    RETURNING id
              """, UUID.class, spot.name(), spot.latitude(), spot.longitude(), spot.description());
    }

    private long countTotalElements() {
        return jdbcClient.sql("SELECT COUNT(*) FROM spot").query(Long.class).single();
    }

    public UUID createSpotByAddingRequest(UUID spotAddingRequestId) {
        return jdbcClient.sql("""
            INSERT INTO spot (name, lat, lon, description)
                 SELECT sar.spot_name, sar.spot_lat, sar.spot_lon, sar.spot_description
                   FROM spot_adding_request sar
                  WHERE sar.id = :spotAddingRequestId
              RETURNING id
        """)
                .param("spotAddingRequestId", spotAddingRequestId)
                .query(UUID.class)
                .single();
    }
}
