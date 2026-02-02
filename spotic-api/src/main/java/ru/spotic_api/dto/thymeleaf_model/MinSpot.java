package ru.spotic_api.dto.thymeleaf_model;

public record MinSpot(
        String id,
        String name,
        double latitude,
        double longitude,
        String insertedAt,
        String updatedAt,
        String description,
        String firstImageLink,
        Double distance
) {
    public String getFormattedDistance() {
        if (distance == null) {
            return "";
        } else if (distance < 1000.0) {
            return "%s м. от вас".formatted(distance.intValue());
        } else {
            return "%s км. от вас".formatted(distance / 1000);
        }
    }
}
