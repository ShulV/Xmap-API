package com.xmap_api.dto.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record DefaultSpotDTO(
        UUID id,
        String name,
        double latitude,
        double longitude,
        String insertedAt,
        String updatedAt,
        String description,
        String city
) {
}
