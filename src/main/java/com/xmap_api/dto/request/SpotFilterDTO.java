package com.xmap_api.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import org.springframework.lang.Nullable;

public record SpotFilterDTO(
        @Nullable
        @Min(1)
        Long cityId,
        @Nullable
        @DecimalMin("-90.0")
        @DecimalMax("90.0")
        Double locationLat,
        @Nullable
        @DecimalMin("-180.0")
        @DecimalMax("180.0")
        Double locationLon,
        @Nullable
        //в метрах
        Long distance
) {
}
