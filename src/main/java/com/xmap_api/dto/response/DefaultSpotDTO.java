package com.xmap_api.dto.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record DefaultSpotDTO(
        UUID id,
        String name,
        Double latitude,
        Double longitude,
        String insertedAt,
        String updatedAt,
        String description,
        String city
) {
}
