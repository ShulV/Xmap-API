package ru.spotic_api.dto.thymeleaf_model;

public record MinSpotAddingRequest(
        String id,
        String spotName,
        String insertedAt,
        String firstImageLink,
        String status
) {
}
