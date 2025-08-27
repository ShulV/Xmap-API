package com.xmap_api.dto.thymeleaf_model;

import com.xmap_api.models.SpotAddingRequest;
import lombok.Builder;

import java.util.Date;
import java.util.List;

@Builder
public record SpotAddingRequestWithImageLinksDTO(
        String spotName,
        double spotLatitude,
        double spotLongitude,
        String status,
        Date insertedAt,
        Date acceptedAt,
        String spotDescription,
        String comment,
        String adderId,
        String acceptorId,
        List<String> spotImageLinks,
        String city
) {
    public SpotAddingRequestWithImageLinksDTO {
    }

    public SpotAddingRequestWithImageLinksDTO(SpotAddingRequest sar, List<String> spotImageLinks) {
        this(sar.getSpotName(), sar.getSpotLatitude(), sar.getSpotLongitude(), sar.getStatus().name(),
                sar.getInsertedAt(), sar.getAcceptedAt(), sar.getSpotDescription(), sar.getComment(),
                sar.getAdder().getId().toString(),
                sar.getAcceptor() == null ? null : sar.getAcceptor().getId().toString(), spotImageLinks,
                sar.getCity().getName());
    }
}
