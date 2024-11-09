package com.xmap_api.controllers;

import com.xmap_api.models.Spot;
import com.xmap_api.services.SpotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/spot")
public class SpotController {
    private final SpotService spotService;

    public SpotController(SpotService spotService) {
        this.spotService = spotService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Spot>> getAll() {
        return ResponseEntity.ok(spotService.getAll());
    }
}
