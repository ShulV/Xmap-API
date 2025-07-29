package com.xmap_api.dao;

import com.xmap_api.dto.inside.DownloadedFileDTO;
import com.xmap_api.exceptions.XmapApiException;
import com.xmap_api.models.S3File;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class S3FileDAO {
    private final JdbcTemplate jdbcTemplate;
    private final JdbcClient jdbcClient;

    public S3FileDAO(JdbcTemplate jdbcTemplate, JdbcClient jdbcClient) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcClient = jdbcClient;
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

    public void batchInsertSpotWithS3Files(List<S3File> s3Files, UUID spotId) {
        List<Object[]> batchData = s3Files.stream()
                .map(s3File -> new Object[]{
                        s3File.getOriginalFileName(),
                        s3File.getFileSize(),
                        s3File.getFileContent(),
                        s3File.getContentType(),
                        s3File.getFileType().name(),
                        spotId
                }).toList();
        jdbcTemplate.batchUpdate("""
            WITH inserted_row AS (
                    INSERT INTO s3_file (original_file_name, file_size, file_content, content_type, file_type)
                     VALUES (?, ?, ?, ?, ?)
                  RETURNING id
            )
            INSERT INTO spot_s3_file (spot_id, s3_file_id)
            VALUES (?, (SELECT id FROM inserted_row))
            """, batchData);
    }

    public void batchInsertSpotAddingRequestWithS3Files(List<S3File> s3Files, UUID spotAddingRequestId) {
        List<Object[]> batchData = s3Files.stream()
                .map(s3File -> new Object[]{
                        s3File.getOriginalFileName(),
                        s3File.getFileSize(),
                        s3File.getFileContent(),
                        s3File.getContentType(),
                        s3File.getFileType().name(),
                        spotAddingRequestId
                }).toList();
        jdbcTemplate.batchUpdate("""
            WITH inserted_row AS (
                    INSERT INTO s3_file (original_file_name, file_size, file_content, content_type, file_type)
                     VALUES (?, ?, ?, ?, ?)
                  RETURNING id
            )
            INSERT INTO spot_adding_request_s3_file (spot_adding_request_id, s3_file_id)
            VALUES (?, (SELECT id FROM inserted_row))
            """, batchData);
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
                        //TODO
                        throw new XmapApiException();
                    }
                },
                s3FileId);
    }

    public List<UUID> getSpotImageLinks(UUID spotId) {
        return jdbcTemplate.queryForList("""
                  SELECT sf.id
                    FROM s3_file sf
               LEFT JOIN spot_s3_file ssf ON ssf.s3_file_id = sf.id
                   WHERE ssf.spot_id = ?
              """, UUID.class, spotId);
    }

    public List<UUID> getSpotAddingRequestImageLinks(UUID spotAddingRequestId) {
        return jdbcClient.sql("""
                  SELECT sf.id
                    FROM s3_file sf
               LEFT JOIN spot_adding_request_s3_file scrsf ON scrsf.s3_file_id = sf.id
                   WHERE scrsf.spot_adding_request_id = :spotAddingRequestId
              """)
                .param("spotAddingRequestId", spotAddingRequestId)
                .query(UUID.class)
                .list();
    }
}
