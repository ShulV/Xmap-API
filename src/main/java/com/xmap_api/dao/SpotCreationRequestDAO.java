package com.xmap_api.dao;

import com.xmap_api.dao.mappers.SpotCreationRequestRowMapper;
import com.xmap_api.dto.response.MinSpotCreationRequest;
import com.xmap_api.models.SpotCreationRequest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class SpotCreationRequestDAO {
    private final JdbcClient jdbcClient;
    private final SpotCreationRequestRowMapper spotCreationRequestRowMapper = new SpotCreationRequestRowMapper();

    public SpotCreationRequestDAO(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public UUID create(SpotCreationRequest scr) {
        return jdbcClient.sql("""
            INSERT INTO spot_creation_request
                (spot_name, spot_lat, spot_lon, status, spot_description, comment, creator_id)
                 VALUES (:spot_name, :spot_lat, :spot_lon, :status, :spot_desc, :comment, :creator_id)
              RETURNING id
        """).param("spot_name", scr.getSpotName())
                .param("spot_lat", scr.getSpotLatitude())
                .param("spot_lon", scr.getSpotLongitude())
                .param("status", scr.getStatus().name())
                .param("spot_desc", scr.getSpotDescription())
                .param("comment", scr.getComment())
                .param("creator_id", scr.getCreator().getId())
                .query(UUID.class)
                .single();
    }

    public List<MinSpotCreationRequest> getWithFirstImageLink(
            UUID creatorId, String fileLinkTemplate, String fileLinkPathParam) {
        return jdbcClient.sql("""
                    WITH ranked_files AS (
                        SELECT scr.*,
                               sf.id AS file_id,
                               ROW_NUMBER() OVER(PARTITION BY scr.id ORDER BY sf.uploaded_at) AS rn
                          FROM spot_creation_request scr
                          JOIN spot_creation_request_s3_file scrsf ON scrsf.spot_creation_request_id = scr.id
                          JOIN s3_file sf ON scrsf.s3_file_id = sf.id
                    )
                    SELECT *
                      FROM ranked_files
                     WHERE rn = 1 and creator_id = :creatorId
               """)
                .param("creatorId", creatorId)
                .query((rs, rowNum) -> new MinSpotCreationRequest(
                        rs.getString("id"),
                        rs.getString("spot_name"),
                        rs.getDouble("spot_lat"),
                        rs.getDouble("spot_lon"),
                        fileLinkTemplate.replace(fileLinkPathParam, rs.getString("file_id")))
                )
                .list();
    }
}
