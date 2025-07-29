package com.xmap_api.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record NewSpotAddingRequestDTO(
        String spotName,
        double spotLat,
        double spotLon,
        String spotDescription,
        String comment,
        List<MultipartFile> files
) {
}
