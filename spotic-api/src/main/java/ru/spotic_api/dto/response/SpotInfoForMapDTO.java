package ru.spotic_api.dto.response;

public record SpotInfoForMapDTO(
        String id,
        double longitude,
        double latitude
) {
}
