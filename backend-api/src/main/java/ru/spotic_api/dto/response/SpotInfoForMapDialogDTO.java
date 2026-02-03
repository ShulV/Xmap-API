package ru.spotic_api.dto.response;

public record SpotInfoForMapDialogDTO(
        String name,
        String firstImageLink,
        Double distance
) {
}
