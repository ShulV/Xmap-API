package com.xmap_api.services;

import com.xmap_api.models.S3File;
import com.xmap_api.repos.S3FileRepo;
import org.springframework.stereotype.Service;

@Service
public class S3FileService {
    private final S3FileRepo s3FileRepo;

    public S3FileService(S3FileRepo s3FileRepo) {
        this.s3FileRepo = s3FileRepo;
    }

    public void createS3File(S3File s3File) {
        s3FileRepo.save(s3File);
    }
}
