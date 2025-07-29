package com.xmap_api.dto.response;

public record MinSpotAddingRequest(
        String id,
        String spotName,
        double spotLatitude,
        double spotLongitude,
        String firstImageLink
) {
}
