package ru.spotic_api.dto.thymeleaf_model;

import ru.spotic_api.models.SpotAddingRequest;
import lombok.Builder;

import java.text.SimpleDateFormat;
import java.util.List;

@Builder
public record SpotAddingRequestWithImageLinksDTO(
        String spotName,
        double spotLatitude,
        double spotLongitude,
        String status,
        String insertedAt,
        String acceptedAt,
        String spotDescription,
        String comment,
        String adderId,
        String acceptorId,
        List<String> spotImageLinks,
        String city,
        String spotId
) {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public SpotAddingRequestWithImageLinksDTO {
    }

    public SpotAddingRequestWithImageLinksDTO(SpotAddingRequest sar, List<String> spotImageLinks) {
        this(sar.getSpotName(),
                sar.getSpotLatitude(),
                sar.getSpotLongitude(),
                sar.getStatus().name(),
                sdf.format(sar.getInsertedAt()),
                sar.getAcceptedAt() == null ? null : sdf.format(sar.getAcceptedAt()),
                sar.getSpotDescription(),
                sar.getComment(),
                sar.getAdder().getId().toString(),
                sar.getAcceptor() == null ? null : sar.getAcceptor().getId().toString(),
                spotImageLinks,
                sar.getCity().getName(),
                sar.getSpot() == null ? null : sar.getSpot().getId().toString());
    }
}
