package com.xmap_api.dto.thymeleaf_model;

import java.util.Date;

public record MinSpot(
        String id,
        String name,
        double latitude,
        double longitude,
        Date insertedAt,
        Date updatedAt,
        String description,
        String firstImageLink
) {
}
