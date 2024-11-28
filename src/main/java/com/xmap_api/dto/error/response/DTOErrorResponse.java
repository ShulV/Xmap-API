package com.xmap_api.dto.error.response;

import lombok.Builder;

@Builder
public record DTOErrorResponse(
        String message
) {
}
