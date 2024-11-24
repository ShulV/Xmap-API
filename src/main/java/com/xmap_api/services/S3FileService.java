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
    private final SpotS3FileService spotS3FileService;

    public S3FileService(S3FileDAO s3FileDAO, SpotS3FileService spotS3FileService) {
        this.s3FileDAO = s3FileDAO;
        this.spotS3FileService = spotS3FileService;
    }

    //@Transactional
    //public UUID createS3File(MultipartFile file, DBCode.S3File.FileType fileType) {
    //    return s3FileDAO.insert(new S3File(file, fileType));
    //}

    @Transactional
    public void createSpotImage(MultipartFile file, UUID spotId) {
        UUID s3FileId =  s3FileDAO.insert(new S3File(file, DBCode.S3File.FileType.SPOT_IMAGE));
        spotS3FileService.insert(spotId, s3FileId);
    }
}
