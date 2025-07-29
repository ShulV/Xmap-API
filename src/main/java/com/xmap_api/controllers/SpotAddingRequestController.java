package com.xmap_api.controllers;

import com.xmap_api.dto.request.NewSpotAddingRequestDTO;
import com.xmap_api.services.SpotAddingRequestService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class SpotAddingRequestController {
    private final SpotAddingRequestService spotAddingRequestService;

    public SpotAddingRequestController(SpotAddingRequestService spotAddingRequestService) {
        this.spotAddingRequestService = spotAddingRequestService;
    }

    @GetMapping("/spot-adding-request")
    public String getSpotAddingRequestPage(Model model) {
        model.addAttribute("formData",
                new NewSpotAddingRequestDTO("", 0, 0, "", "", new ArrayList<>(0)));
        return "spot-adding-request";
    }

    @PostMapping("/spot-adding-request")
    public String create(@AuthenticationPrincipal UserDetails userDetails, NewSpotAddingRequestDTO formData) {
        spotAddingRequestService.create(formData, userDetails.getUsername());
        return "redirect:/spot-adding-request-list";//todo
    }

    @GetMapping("/spot-adding-request-list")
    public String getSpotAddingRequestsPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("minSpotAddingRequestList",
                spotAddingRequestService.getWithFirstImageLink(userDetails.getUsername()));
        return "spot-adding-request-list";
    }

}
