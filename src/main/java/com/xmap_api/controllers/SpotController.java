package com.xmap_api.controllers;

import com.xmap_api.services.SpotService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Controller
public class SpotController {
    private final SpotService spotService;

    public SpotController(SpotService spotService) {
        this.spotService = spotService;
    }

    @GetMapping("/spots")
    public String getSpotListPage(Model model) {
        model.addAttribute("spotList", spotService.getDefaultAll(Pageable.ofSize(100)));
        return "spots";
    }

    @GetMapping("/spot/{spotId}")
    public String getSpotPage(Model model, @PathVariable("spotId") UUID spotId) {
        model.addAttribute("spot", spotService.getSpotWithImageLinks(spotId));
        return "spot";
    }
}
