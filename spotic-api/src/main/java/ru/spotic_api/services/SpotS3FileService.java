package ru.spotic_api.services;

import ru.spotic_api.dao.SpotS3FileDAO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpotS3FileService {
    private final SpotS3FileDAO spotS3FileDAO;

    public SpotS3FileService(SpotS3FileDAO spotS3FileDAO) {
        this.spotS3FileDAO = spotS3FileDAO;
    }

    public void insert(UUID spotId, UUID s3FileId) {
        spotS3FileDAO.insert(spotId, s3FileId);
    }
}
