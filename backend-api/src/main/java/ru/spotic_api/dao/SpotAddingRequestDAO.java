package ru.spotic_api.dao;

import ru.spotic_api.dao.mappers.SpotAddingRequestRowMapper;
import ru.spotic_api.dto.thymeleaf_model.MinSpotAddingRequest;
import ru.spotic_api.models.SpotAddingRequest;
import ru.spotic_api.models.status.SpotAddingRequestStatus;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

@Repository
public class SpotAddingRequestDAO {
    private final JdbcClient jdbcClient;
    private final SpotAddingRequestRowMapper spotAddingRequestRowMapper = new SpotAddingRequestRowMapper();

    public SpotAddingRequestDAO(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public UUID create(SpotAddingRequest sar) {
        return jdbcClient.sql("""
            INSERT INTO spot_adding_request
                (spot_name, spot_lat, spot_lon, status, spot_description, comment, adder_id)
                 VALUES (:spot_name, :spot_lat, :spot_lon, :status, :spot_desc, :comment, :adder_id)
              RETURNING id
        """).param("spot_name", sar.getSpotName())
                .param("spot_lat", sar.getSpotLatitude())
                .param("spot_lon", sar.getSpotLongitude())
                .param("status", sar.getStatus().name())
                .param("spot_desc", sar.getSpotDescription())
                .param("comment", sar.getComment())
                .param("adder_id", sar.getAdder().getId())
                .query(UUID.class)
                .single();
    }

    public List<MinSpotAddingRequest> getWithFirstImageLinkByUserId(
            UUID adderId, String fileLinkTemplate, String fileLinkPathParam) {
        return jdbcClient.sql("""
                    WITH ranked_files AS (
                        SELECT scr.id, scr.spot_name, scr.inserted_at, scr.adder_id, scr.status,
                               sf.id AS file_id,
                               ROW_NUMBER() OVER(PARTITION BY scr.id ORDER BY sf.uploaded_at) AS rn
                          FROM spot_adding_request scr
                          JOIN spot_adding_request_s3_file scrsf ON scrsf.spot_adding_request_id = scr.id
                          JOIN s3_file sf ON scrsf.s3_file_id = sf.id
                    )
                    SELECT id, spot_name, inserted_at, file_id, status
                      FROM ranked_files
                     WHERE rn = 1
                       AND adder_id = :adderId
                  ORDER BY inserted_at DESC
               """)
                .param("adderId", adderId)
                .query((rs, rowNum) -> new MinSpotAddingRequest(
                        rs.getString("id"),
                        rs.getString("spot_name"),
                        new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("inserted_at")),
                        fileLinkTemplate.replace(fileLinkPathParam, rs.getString("file_id")),
                        rs.getString("status"))
                )
                .list();
    }

    public List<MinSpotAddingRequest> getWithFirstImageLinkByStatusList(
            @NotEmpty List<String> statusList, String fileLinkTemplate, String fileLinkPathParam) {
        return jdbcClient.sql("""
                    WITH ranked_files AS (
                        SELECT scr.id, scr.spot_name, scr.inserted_at, scr.status,
                               sf.id AS file_id,
                               ROW_NUMBER() OVER(PARTITION BY scr.id ORDER BY sf.uploaded_at) AS rn
                          FROM spot_adding_request scr
                          JOIN spot_adding_request_s3_file scrsf ON scrsf.spot_adding_request_id = scr.id
                          JOIN s3_file sf ON scrsf.s3_file_id = sf.id
                    )
                    SELECT id, spot_name, inserted_at, file_id, status
                      FROM ranked_files
                     WHERE rn = 1
                       AND status IN (:statusList)
                  ORDER BY inserted_at DESC
               """)
                .param("statusList", statusList)
                .query((rs, rowNum) -> new MinSpotAddingRequest(
                        rs.getString("id"),
                        rs.getString("spot_name"),
                        new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("inserted_at")),
                        fileLinkTemplate.replace(fileLinkPathParam, rs.getString("file_id")),
                        rs.getString("status"))
                )
                .list();
    }

    public SpotAddingRequest getById(UUID id) {
        return jdbcClient.sql("""
                    SELECT sar.*,
                           c.id AS "city_id", c.name AS "city_name"
                      FROM spot_adding_request sar
                      JOIN city c ON sar.city_id = c.id
                     WHERE sar.id = :id
               """)
                .param("id", id)
                .query(spotAddingRequestRowMapper)
                .single();
    }

    public void updateStatus(UUID spotAddingRequestId, SpotAddingRequestStatus status) {
        jdbcClient.sql("UPDATE spot_adding_request SET status = :status WHERE id = :id")
                .param("status", status.name())
                .param("id", spotAddingRequestId)
                .update();
    }

    public void updateSpotId(UUID spotAddingRequestId, UUID spotId) {
        jdbcClient.sql("UPDATE spot_adding_request SET spot_id = :spotId WHERE id = :id")
                .param("spotId", spotId)
                .param("id", spotAddingRequestId)
                .update();
    }
}
