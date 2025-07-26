package com.xmap_api.controllers;

import com.xmap_api.services.SpotService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
