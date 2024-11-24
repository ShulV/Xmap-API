package com.xmap_api.dto.response;

import lombok.Builder;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Builder
public record SpotWithImageLinksDTO(
        UUID id,
        String name,
        double latitude,
        double longitude,
        Date insertedAt,
        Date updatedAt,
        String description,
        List<String> spotImageLinks
) {
    public SpotWithImageLinksDTO(DefaultSpotDTO dto, List<String> spotImageLinks) {
        this(dto.id(), dto.name(), dto.latitude(), dto.longitude(), dto.insertedAt(), dto.updatedAt(), dto.description(),
                spotImageLinks);
    }
}
