package com.xmap_api.dao;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class SpotAddingRequestS3FileDAO {
    private final JdbcClient jdbcClient;

    public SpotAddingRequestS3FileDAO(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public void insert(UUID spotAddingRequestId, UUID s3FileId) {
        jdbcClient.sql("""
                INSERT INTO spot_adding_request_s3_file (spot_adding_request_id, s3_file_id)
                     VALUES (:spotAddingRequestId, :s3FileId)
                """)
                .param("spotAddingRequestId", spotAddingRequestId)
                .param("s3FileId", s3FileId);
    }
}