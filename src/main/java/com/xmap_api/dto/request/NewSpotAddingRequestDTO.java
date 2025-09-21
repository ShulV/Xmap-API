package com.xmap_api.dto.request;

import com.xmap_api.aop.ValidFilesCount;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record NewSpotAddingRequestDTO(
        @NotBlank
        @Size(min = 3, max = 50)
        String spotName,
        @DecimalMin("-90.0")
        @DecimalMax("90.0")
        Double spotLat,
        @DecimalMin("-180.0")
        @DecimalMax("180.0")
        Double spotLon,
        @NotBlank
        @Size(min = 10, max = 300)
        String spotDescription,
        @ValidFilesCount(min = 1, max = 5, message = "Должно быть от 1 до 5 файлов")
        List<MultipartFile> files
) {
}
