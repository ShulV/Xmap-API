package com.xmap_api.models;

import com.xmap_api.exceptions.XmapApiException;
import com.xmap_api.util.DBCode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "s3_file")
@Getter
@Setter
@NoArgsConstructor
public class S3File {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "original_file_name")
    private String originalFileName;

    @Column(name = "file_size")
    private long fileSize;

    @Column(name = "file_content")
    private byte[] fileContent;

    @Column(name = "file_extension")
    private String fileExtension;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "file_type")
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

    public S3File(MultipartFile file, DBCode.S3File.FileType fileType) {
        try {
            this.originalFileName = FilenameUtils.removeExtension(file.getOriginalFilename());
            this.fileSize = file.getSize();
            this.fileContent = file.getBytes();
            this.fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            this.contentType = file.getContentType();
            this.fileType = fileType;
        } catch (IOException e) {
            //TODO log
            throw new XmapApiException();
        }
    }
}
