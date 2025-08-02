package com.xmap_api.controllers.rest;

import com.xmap_api.services.SpotAddingRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class SpotAddingRequestRestController {
    private final SpotAddingRequestService spotAddingRequestService;

    public SpotAddingRequestRestController(SpotAddingRequestService spotAddingRequestService) {
        this.spotAddingRequestService = spotAddingRequestService;
    }

    @PatchMapping("/spot-adding-request/accept")
    @ResponseBody
    public ResponseEntity<String> accept(@RequestParam String spotAddingRequestId) {
        spotAddingRequestService.accept(UUID.fromString(spotAddingRequestId));
        return ResponseEntity.ok("accepted");
    }

}
