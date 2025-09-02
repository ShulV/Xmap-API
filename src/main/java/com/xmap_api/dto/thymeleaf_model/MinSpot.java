package com.xmap_api.dto.thymeleaf_model;

public record MinSpot(
        String id,
        String name,
        double latitude,
        double longitude,
        String insertedAt,
        String updatedAt,
        String description,
        String firstImageLink
) {
}
