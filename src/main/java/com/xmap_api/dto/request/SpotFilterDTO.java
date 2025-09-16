package com.xmap_api.dto.request;

import org.springframework.lang.Nullable;

public record SpotFilterDTO(
        @Nullable
        Long cityId
) {
}
