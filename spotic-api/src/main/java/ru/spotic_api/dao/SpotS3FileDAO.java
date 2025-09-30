package ru.spotic_api.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class SpotS3FileDAO {
    private final JdbcTemplate jdbcTemplate;

    public SpotS3FileDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(UUID spotId, UUID s3FileId) {
        jdbcTemplate.update("""
                INSERT INTO spot_s3_file (spot_id, s3_file_id)
                     VALUES (?, ?)
                """, spotId, s3FileId);
    }
}
