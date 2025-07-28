package com.xmap_api.dao;

import com.xmap_api.models.SpotCreationRequest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class SpotCreationRequestDAO {
    private final JdbcClient jdbcClient;

    public SpotCreationRequestDAO(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public UUID create(SpotCreationRequest scr) {
        return jdbcClient.sql("""
        INSERT INTO spot_creation_request
            (spot_name, spot_lat, spot_lon, spot_description, comment, creator_id)
             VALUES (:spot_name, :spot_lat, :spot_lon, :spot_desc, :comment, :creator_id)
          RETURNING id
        """).param("spot_name", scr.getSpotName())
                .param("spot_lat", scr.getSpotLatitude())
                .param("spot_lon", scr.getSpotLongitude())
                .param("spot_desc", scr.getSpotDescription())
                .param("comment", scr.getComment())
                .param("creator_id", scr.getCreator().getId())
                .query(UUID.class)
                .single();
    }
}
