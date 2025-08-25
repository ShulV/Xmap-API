package com.xmap_api.controllers;

import com.xmap_api.exceptions.XmapApiException;
import com.xmap_api.services.SpotService;
import com.xmap_api.util.UICode;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
                                  @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                  @RequestParam(required = false, defaultValue = "3") Integer pageSize,
                                  @RequestParam String viewMode) {
        if (!UICode.Spots.ViewMode.ALL.contains(viewMode)) {
            throw new XmapApiException("Unexpected view mode");
        }
        model.addAttribute("viewMode", viewMode);
        if (UICode.Spots.ViewMode.CARDS.equals(viewMode)) {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            model.addAttribute("spotList", spotService.getWithFirstImage(pageable));
        }
        model.addAttribute("activePage", "spots");
        return "spots";
    }

    @GetMapping("/spot/{spotId}")
    public String getSpotPage(Model model, @PathVariable("spotId") UUID spotId) {
        model.addAttribute("spot", spotService.getSpotWithImageLinks(spotId));
        return "spot";
    }
}
