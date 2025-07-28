package com.xmap_api.controllers;

import com.xmap_api.dto.request.NewSpotCreationRequestDTO;
import com.xmap_api.services.SpotCreationRequestService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class SpotCreationRequestController {
    private final SpotCreationRequestService spotCreationRequestService;

    public SpotCreationRequestController(SpotCreationRequestService spotCreationRequestService) {
        this.spotCreationRequestService = spotCreationRequestService;
    }

    @GetMapping("/spot-creation-request")
    public String getSpotCreationRequestPage(Model model) {
        model.addAttribute("formData", new NewSpotCreationRequestDTO("", 0, 0, "", "", new ArrayList<>(0)));
        return "spot-creation-request";
    }

    @PostMapping("/spot-creation-request")
    public String create(@AuthenticationPrincipal UserDetails userDetails, NewSpotCreationRequestDTO formData) {
        spotCreationRequestService.create(formData, userDetails.getUsername());
        return "/spots";//todo
    }
}
