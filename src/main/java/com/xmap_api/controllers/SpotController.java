package com.xmap_api.controllers;

import com.xmap_api.dto.response.DefaultSpotDTO;
import com.xmap_api.dto.response.SpotWithImageLinksDTO;
import com.xmap_api.services.SpotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/spot")
public class SpotController {
    private final SpotService spotService;

    public SpotController(SpotService spotService) {
        this.spotService = spotService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<DefaultSpotDTO>> getAll() {
        return ResponseEntity.ok(spotService.getDefaultAll());
    }

    @GetMapping("/{spotId}")
    public ResponseEntity<SpotWithImageLinksDTO> getSpotInfo(@PathVariable("spotId") UUID spotId) {
        return ResponseEntity.ok(spotService.getSpotWithImageLinks(spotId));
    }
}
