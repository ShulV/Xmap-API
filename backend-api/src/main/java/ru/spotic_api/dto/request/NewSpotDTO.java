package ru.spotic_api.dto.request;

import lombok.Builder;

@Builder
public record NewSpotDTO(
        String name,
        double latitude,
        double longitude,
        String description
) {
}
