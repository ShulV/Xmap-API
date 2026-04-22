package ru.spotic_api.controllers;

import ru.spotic_api.exceptions.XmapApiException;
import ru.spotic_api.services.SpotService;
import ru.spotic_api.util.UICode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class SpotController {
    private final SpotService spotService;

    public SpotController(SpotService spotService) {
        this.spotService = spotService;
    }

    @GetMapping("/spots")
    public String getSpotListPage(Model model,
                                  @RequestParam String viewMode) {
        if (!UICode.Spots.ViewMode.ALL.contains(viewMode)) {
            throw new XmapApiException("Unexpected view mode");
        }
        model.addAttribute("viewMode", viewMode);
        model.addAttribute("activePage", "spots");
        return "spot-list";
    }

    @GetMapping("/spot/{spotId}")
    public String getSpotPage(Model model, @PathVariable("spotId") UUID spotId) {
        model.addAttribute("spot", spotService.getSpotWithImageLinks(spotId));
        return "spot-instance";
    }
}
