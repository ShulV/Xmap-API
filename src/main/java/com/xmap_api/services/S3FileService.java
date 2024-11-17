package com.xmap_api.services;

import com.xmap_api.dao.S3FileDAO;
import com.xmap_api.models.S3File;
import com.xmap_api.util.DBCode;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class S3FileService {
    private final S3FileDAO s3FileDAO;

    public S3FileService(S3FileDAO s3FileDAO) {
        this.s3FileDAO = s3FileDAO;
    }

    @Transactional
    public UUID createS3File(MultipartFile file, DBCode.S3File.FileType fileType) {
        UUID newId = s3FileDAO.insert(new S3File(file, fileType));
        switch (fileType) {
            case SPOT_IMAGE -> {}
        }
        return newId;
    }
}
