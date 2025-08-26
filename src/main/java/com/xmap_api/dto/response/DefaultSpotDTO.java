package com.xmap_api.dto.response;

import lombok.Builder;

import java.util.Date;
import java.util.UUID;

@Builder
public record DefaultSpotDTO(
        UUID id,
        String name,
        double latitude,
        double longitude,
        Date insertedAt,
        Date updatedAt,
        String description,
        String city
) {
}
