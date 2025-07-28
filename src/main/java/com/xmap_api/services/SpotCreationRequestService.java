package com.xmap_api.services;

import com.xmap_api.dao.SpotCreationRequestDAO;
import com.xmap_api.dao.UserDAO;
import com.xmap_api.dto.request.NewSpotCreationRequestDTO;
import com.xmap_api.models.SpotCreationRequest;
import com.xmap_api.models.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpotCreationRequestService {
    private final SpotCreationRequestDAO spotCreationRequestDAO;
    private final S3FileService s3FileService;
    private final UserService userService;
    private final UserDAO userDAO;

    public SpotCreationRequestService(SpotCreationRequestDAO spotCreationRequestDAO, S3FileService s3FileService, UserService userService, UserDAO userDAO) {
        this.spotCreationRequestDAO = spotCreationRequestDAO;
        this.s3FileService = s3FileService;
        this.userService = userService;
        this.userDAO = userDAO;
    }

    @Transactional
    public UUID create(NewSpotCreationRequestDTO requestDTO, String username) {
        UUID userId = userService.getId(username);
        SpotCreationRequest spotCreationRequest = SpotCreationRequest.builder()
                .spotName(requestDTO.spotName())
                .spotLatitude(requestDTO.spotLat())
                .spotLongitude(requestDTO.spotLon())
                .spotDescription(requestDTO.spotDescription())
                .creator(new User(userId))
                .comment(requestDTO.comment())
                .build();
        UUID spotCreationRequestId = spotCreationRequestDAO.create(spotCreationRequest);
        s3FileService.createSpotCreationRequestImages(requestDTO.files(), spotCreationRequestId);
        return spotCreationRequestId;
    }
}
