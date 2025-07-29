package com.xmap_api.services;

import com.xmap_api.dao.SpotAddingRequestDAO;
import com.xmap_api.dao.UserDAO;
import com.xmap_api.dto.request.NewSpotAddingRequestDTO;
import com.xmap_api.dto.response.MinSpotAddingRequest;
import com.xmap_api.models.SpotAddingRequest;
import com.xmap_api.models.User;
import com.xmap_api.models.status.SpotAddingRequestStatus;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SpotAddingRequestService {

    @Value("${xmap-api.s3-file.download-link-template}")
    private String s3FileDownloadLinkTemplate;
    @Value("${xmap-api.s3-file.download-link-path-param}")
    private String s3FileDownloadLinkPathParam;

    private final SpotAddingRequestDAO spotAddingRequestDAO;
    private final S3FileService s3FileService;
    private final UserService userService;
    private final UserDAO userDAO;

    public SpotAddingRequestService(SpotAddingRequestDAO spotAddingRequestDAO, S3FileService s3FileService, UserService userService, UserDAO userDAO) {
        this.spotAddingRequestDAO = spotAddingRequestDAO;
        this.s3FileService = s3FileService;
        this.userService = userService;
        this.userDAO = userDAO;
    }

    @Transactional
    public UUID create(NewSpotAddingRequestDTO requestDTO, String username) {
        UUID userId = userService.getId(username);
        SpotAddingRequest spotAddingRequest = SpotAddingRequest.builder()
                .spotName(requestDTO.spotName())
                .spotLatitude(requestDTO.spotLat())
                .spotLongitude(requestDTO.spotLon())
                .status(SpotAddingRequestStatus.PENDING_APPROVAL)
                .spotDescription(requestDTO.spotDescription())
                .adder(new User(userId))
                .comment(requestDTO.comment())
                .build();
        UUID spotAddingRequestId = spotAddingRequestDAO.create(spotAddingRequest);
        s3FileService.createSpotAddingRequestImages(requestDTO.files(), spotAddingRequestId);
        return spotAddingRequestId;
    }

    public List<MinSpotAddingRequest> getWithFirstImageLink(String username) {
        UUID userId = userService.getId(username);
        return spotAddingRequestDAO.getWithFirstImageLink(
                userId, s3FileDownloadLinkTemplate, s3FileDownloadLinkPathParam);
    }
}
