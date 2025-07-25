package com.xmap_api.dto.response.error;

import lombok.Builder;

@Builder
public record ErrorDTO(
        String message,
        String log //TODO tmp
) {
}
