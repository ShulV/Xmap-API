package com.xmap_api.dto.request;

import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record NewSpotAddingRequestDTO(
        String spotName,
        double spotLat,
        double spotLon,
        String spotDescription,
        @Size(min = 1, max = 5)
        List<MultipartFile> files
) {
}
