package com.xmap_api.services;

import com.xmap_api.dao.SpotCreationRequestDAO;
import com.xmap_api.dao.UserDAO;
import com.xmap_api.dto.request.NewSpotCreationRequestDTO;
import com.xmap_api.dto.response.MinSpotCreationRequest;
import com.xmap_api.models.SpotCreationRequest;
import com.xmap_api.models.User;
import com.xmap_api.models.status.SpotCreationRequestStatus;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SpotCreationRequestService {

    @Value("${xmap-api.s3-file.download-link-template}")
    private String s3FileDownloadLinkTemplate;
    @Value("${xmap-api.s3-file.download-link-path-param}")
    private String s3FileDownloadLinkPathParam;

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
                .status(SpotCreationRequestStatus.PENDING_APPROVAL)
                .spotDescription(requestDTO.spotDescription())
                .creator(new User(userId))
                .comment(requestDTO.comment())
                .build();
        UUID spotCreationRequestId = spotCreationRequestDAO.create(spotCreationRequest);
        s3FileService.createSpotCreationRequestImages(requestDTO.files(), spotCreationRequestId);
        return spotCreationRequestId;
    }

    public List<MinSpotCreationRequest> getWithFirstImageLink(String username) {
        UUID userId = userService.getId(username);
        return spotCreationRequestDAO.getWithFirstImageLink(
                userId, s3FileDownloadLinkTemplate, s3FileDownloadLinkPathParam);
    }
}
