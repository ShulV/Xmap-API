package com.xmap_api.dto.thymeleaf_model;

public record MinSpotAddingRequest(
        String id,
        String spotName,
        double spotLatitude,
        double spotLongitude,
        String firstImageLink
) {
}
