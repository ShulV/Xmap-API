package com.xmap_api.dto.inside;

import lombok.Builder;

@Builder
public record DownloadedFileDTO(
        byte[] content,
        String returnedFilename,
        String contentType
) {
}
