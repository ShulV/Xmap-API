package ru.spotic_api.controllers.rest;

import ru.spotic_api.models.status.SpotAddingRequestStatus;
import ru.spotic_api.services.SpotAddingRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class SpotAddingRequestRestController {
    private final SpotAddingRequestService spotAddingRequestService;

    public SpotAddingRequestRestController(SpotAddingRequestService spotAddingRequestService) {
        this.spotAddingRequestService = spotAddingRequestService;
    }

    @PatchMapping("/api/spot-adding-request/change-status/{status}")
    @ResponseBody
    public ResponseEntity<String> changeStatus(@PathVariable SpotAddingRequestStatus status,
                                               @RequestParam String spotAddingRequestId) {
        spotAddingRequestService.moderate(UUID.fromString(spotAddingRequestId), status);
        return ResponseEntity.ok(status.name());
    }

}
