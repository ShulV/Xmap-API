package com.xmap_api.dao;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class SpotCreationRequestS3FileDAO {
    private final JdbcClient jdbcClient;

    public SpotCreationRequestS3FileDAO(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public void insert(UUID spotCreationRequestId, UUID s3FileId) {
        jdbcClient.sql("""
                INSERT INTO spot_creation_request_s3_file (spot_creation_request_id, s3_file_id)
                     VALUES (:spotCreationRequestId, :s3FileId)
                """)
                .param("spotCreationRequestId", spotCreationRequestId)
                .param("s3FileId", s3FileId);
    }
}