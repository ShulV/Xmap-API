package com.xmap_api.dao;

import com.xmap_api.dto.inside.DownloadedFileDTO;
import com.xmap_api.models.S3File;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.UUID;

@Repository
public class S3FileDAO {
    private final JdbcTemplate jdbcTemplate;

    public S3FileDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UUID insert(S3File s3File) {
        return jdbcTemplate.queryForObject("""
                INSERT INTO s3_file (original_file_name, file_size, file_content, content_type, file_type)
                     VALUES (?, ?, ?, ?, ?)
                  RETURNING id
                """, UUID.class,
                s3File.getOriginalFileName(), s3File.getFileSize(), s3File.getFileContent(), s3File.getContentType(),
                s3File.getFileType().name());
    }

    public DownloadedFileDTO getForDownloading(UUID s3FileId) {
        return jdbcTemplate.query("""
                SELECT file_content, original_file_name, content_type FROM s3_file
                 WHERE id = ?
                """,
                rs -> {
                    if (rs.next()) {
                        return DownloadedFileDTO.builder()
                                .content(rs.getBytes("file_content"))
                                .returnedFilename(rs.getString("original_file_name"))
                                .contentType(rs.getString("content_type"))
                                .build();
                    } else {
                        throw new SQLException("Cannot find s3_file with id=" + s3FileId);
                    }
                },
                s3FileId);
    }
}
