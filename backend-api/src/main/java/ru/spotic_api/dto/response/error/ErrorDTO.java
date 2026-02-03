package ru.spotic_api.dto.response.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorDTO(
        String message,
        String log //TODO tmp
) {
}
