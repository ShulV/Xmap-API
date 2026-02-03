package ru.spotic_api.dto.inside;

import lombok.Builder;

@Builder
public record DownloadedFileDTO(
        byte[] content,
        String returnedFilename,
        String contentType
) {
}
