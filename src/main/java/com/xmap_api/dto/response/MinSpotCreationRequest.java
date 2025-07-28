package com.xmap_api.dto.response;

public record MinSpotCreationRequest(
        String id,
        String spotName,
        double spotLatitude,
        double spotLongitude,
        String firstImageLink
) {
}
