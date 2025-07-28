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
import java.util.List;
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

    @ManyToMany(mappedBy = "s3Files")
    private List<Spot> spots;

    public S3File(MultipartFile file, DBCode.S3File.FileType fileType) {
        try {
            this.originalFileName = file.getOriginalFilename();
            this.fileSize = file.getSize();
            this.fileContent = file.getBytes();
            this.contentType = file.getContentType();
            this.fileType = fileType;
        } catch (IOException e) {
            //TODO log
            throw new XmapApiException();
        }
    }

    public S3File(UUID id) {
        this.id = id;
    }

    public String getFileExtension() {
        return FilenameUtils.getExtension(originalFileName);
    }

    public String getFileName() {
        return FilenameUtils.removeExtension(originalFileName);
    }
}
