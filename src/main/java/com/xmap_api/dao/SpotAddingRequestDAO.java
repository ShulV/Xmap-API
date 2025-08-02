package com.xmap_api.dao;

import com.xmap_api.dao.mappers.SpotAddingRequestRowMapper;
import com.xmap_api.dto.thymeleaf_model.MinSpotAddingRequest;
import com.xmap_api.models.SpotAddingRequest;
import com.xmap_api.models.status.SpotAddingRequestStatus;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class SpotAddingRequestDAO {
    private final JdbcClient jdbcClient;
    private final SpotAddingRequestRowMapper spotAddingRequestRowMapper = new SpotAddingRequestRowMapper();

    public SpotAddingRequestDAO(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public UUID create(SpotAddingRequest scr) {
        return jdbcClient.sql("""
            INSERT INTO spot_adding_request
                (spot_name, spot_lat, spot_lon, status, spot_description, comment, adder_id)
                 VALUES (:spot_name, :spot_lat, :spot_lon, :status, :spot_desc, :comment, :adder_id)
              RETURNING id
        """).param("spot_name", scr.getSpotName())
                .param("spot_lat", scr.getSpotLatitude())
                .param("spot_lon", scr.getSpotLongitude())
                .param("status", scr.getStatus().name())
                .param("spot_desc", scr.getSpotDescription())
                .param("comment", scr.getComment())
                .param("adder_id", scr.getAdder().getId())
                .query(UUID.class)
                .single();
    }

    public List<MinSpotAddingRequest> getWithFirstImageLinkByUserId(
            UUID adderId, String fileLinkTemplate, String fileLinkPathParam) {
        return jdbcClient.sql("""
                    WITH ranked_files AS (
                        SELECT scr.*,
                               sf.id AS file_id,
                               ROW_NUMBER() OVER(PARTITION BY scr.id ORDER BY sf.uploaded_at) AS rn
                          FROM spot_adding_request scr
                          JOIN spot_adding_request_s3_file scrsf ON scrsf.spot_adding_request_id = scr.id
                          JOIN s3_file sf ON scrsf.s3_file_id = sf.id
                    )
                    SELECT *
                      FROM ranked_files
                     WHERE rn = 1
                       AND adder_id = :adderId
               """)
                .param("adderId", adderId)
                .query((rs, rowNum) -> new MinSpotAddingRequest(
                        rs.getString("id"),
                        rs.getString("spot_name"),
                        rs.getDouble("spot_lat"),
                        rs.getDouble("spot_lon"),
                        fileLinkTemplate.replace(fileLinkPathParam, rs.getString("file_id")))
                )
                .list();
    }

    public List<MinSpotAddingRequest> getWithFirstImageLinkByStatusList(
            @NotEmpty List<String> statusList, String fileLinkTemplate, String fileLinkPathParam) {
        return jdbcClient.sql("""
                    WITH ranked_files AS (
                        SELECT scr.*,
                               sf.id AS file_id,
                               ROW_NUMBER() OVER(PARTITION BY scr.id ORDER BY sf.uploaded_at) AS rn
                          FROM spot_adding_request scr
                          JOIN spot_adding_request_s3_file scrsf ON scrsf.spot_adding_request_id = scr.id
                          JOIN s3_file sf ON scrsf.s3_file_id = sf.id
                    )
                    SELECT *
                      FROM ranked_files
                     WHERE rn = 1
                       AND status IN (:statusList)
               """)
                .param("statusList", statusList)
                .query((rs, rowNum) -> new MinSpotAddingRequest(
                        rs.getString("id"),
                        rs.getString("spot_name"),
                        rs.getDouble("spot_lat"),
                        rs.getDouble("spot_lon"),
                        fileLinkTemplate.replace(fileLinkPathParam, rs.getString("file_id")))
                )
                .list();
    }

    public SpotAddingRequest getById(UUID id) {
        return jdbcClient.sql("SELECT * FROM spot_adding_request WHERE id = :id")
                .param("id", id)
                .query(spotAddingRequestRowMapper)
                .single();
    }

    public void accept(UUID spotAddingRequestId) {
        jdbcClient.sql("UPDATE spot_adding_request SET status = :status WHERE id = :id")
                .param("status", SpotAddingRequestStatus.ACCEPTED.name())
                .param("id", spotAddingRequestId)
                .update();
    }
}
