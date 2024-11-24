package com.xmap_api.dao;

import com.xmap_api.models.S3File;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class S3FileDAO {
    private final JdbcTemplate jdbcTemplate;

    public S3FileDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UUID insert(S3File s3File) {
        return jdbcTemplate.queryForObject("""
                INSERT INTO s3_file (original_file_name, file_size, file_content, file_extension, content_type, file_type)
                     VALUES (?, ?, ?, ?, ?, ?)
                  RETURNING id
                """, UUID.class,
                s3File.getOriginalFileName(), s3File.getFileSize(), s3File.getFileContent(), s3File.getFileExtension(),
                s3File.getContentType(), s3File.getFileType().name());
    }
}
