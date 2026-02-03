package ru.spotic_api.services;

import ru.spotic_api.dao.SpotAddingRequestS3FileDAO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpotAddingRequestS3FileService {
    private final SpotAddingRequestS3FileDAO spotAddingRequestS3FileDAO;

    public SpotAddingRequestS3FileService(SpotAddingRequestS3FileDAO spotAddingRequestS3FileDAO) {
        this.spotAddingRequestS3FileDAO = spotAddingRequestS3FileDAO;
    }

    public void insert(UUID spotAddingRequestId, UUID s3FileId) {
        spotAddingRequestS3FileDAO.insert(spotAddingRequestId, s3FileId);
    }
}
