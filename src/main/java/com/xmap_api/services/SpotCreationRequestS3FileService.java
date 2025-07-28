package com.xmap_api.services;

import com.xmap_api.dao.SpotCreationRequestS3FileDAO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpotCreationRequestS3FileService {
    private final SpotCreationRequestS3FileDAO spotCreationRequestS3FileDAO;

    public SpotCreationRequestS3FileService(SpotCreationRequestS3FileDAO spotCreationRequestS3FileDAO) {
        this.spotCreationRequestS3FileDAO = spotCreationRequestS3FileDAO;
    }

    public void insert(UUID spotCreationRequestId, UUID s3FileId) {
        spotCreationRequestS3FileDAO.insert(spotCreationRequestId, s3FileId);
    }
}
