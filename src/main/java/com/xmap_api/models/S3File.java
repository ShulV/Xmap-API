package com.xmap_api.models;

import com.xmap_api.util.DBCode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "s3_file")
@Getter
@Setter
public class S3File {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "original_file_name", nullable = false)
    private String originalFileName;

    @Column(name = "file_size", nullable = false)
    private long fileSize;

    @Column(name = "file_content")
    private byte[] fileContent;

    @Column(name = "file_extension", nullable = false)
    private String fileExtension;

    @Column(name = "file_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DBCode.S3File.FileType fileType;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadAt;

    @Column(name = "s3_object_key")
    private UUID s3ObjectKey;

    @Column(name = "uploaded_in_s3")
    private boolean uploadedInS3;

    @Column(name = "uploaded_in_s3_at")
    private LocalDateTime uploadedInS3At;
}
