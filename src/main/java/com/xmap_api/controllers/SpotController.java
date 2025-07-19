package com.xmap_api.controllers;

import com.xmap_api.services.SpotService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpotController {
    private final SpotService spotService;

    public SpotController(SpotService spotService) {
        this.spotService = spotService;
    }
    @GetMapping("/spots")
    public String getSpotListPage() {
        return "spots";
    }
}
