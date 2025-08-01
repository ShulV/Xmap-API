package com.xmap_api.controllers;

import com.xmap_api.dto.request.NewSpotAddingRequestDTO;
import com.xmap_api.dto.thymeleaf_model.SpotAddingRequestWithImageLinksDTO;
import com.xmap_api.models.status.SpotAddingRequestStatus;
import com.xmap_api.security.SecurityUtil;
import com.xmap_api.services.S3FileService;
import com.xmap_api.services.SpotAddingRequestService;
import com.xmap_api.util.DBCode;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class SpotAddingRequestController {
    private final SpotAddingRequestService spotAddingRequestService;
    private final S3FileService s3FileService;

    public SpotAddingRequestController(SpotAddingRequestService spotAddingRequestService, S3FileService s3FileService) {
        this.spotAddingRequestService = spotAddingRequestService;
        this.s3FileService = s3FileService;
    }

    @GetMapping("/spot-adding-request")
    public String getCreatingPage(Model model) {
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
    public String getRequestListPage(@AuthenticationPrincipal UserDetails userDetails,
                                     @RequestParam(required = false) String status,
                                     Model model) {
        if (SpotAddingRequestStatus.PENDING_APPROVAL.name().equals(status)) {
            SecurityUtil.hasAuthority(DBCode.Authority.ACCEPTOR.name());
            model.addAttribute("minSpotAddingRequestList",
                    spotAddingRequestService.getWithFirstImageLinkByStatusList(List.of(status)));
        } else {
            model.addAttribute("minSpotAddingRequestList",
                    spotAddingRequestService.getWithFirstImageLinkByUsername(userDetails.getUsername()));
        }

        return "spot-adding-request-list";
    }

    @GetMapping("/spot-adding-request/{id}")
    public String getInstancePage(@PathVariable UUID id, Model model) {
        SpotAddingRequestWithImageLinksDTO dto = new SpotAddingRequestWithImageLinksDTO(
                spotAddingRequestService.getById(id),
                s3FileService.getSpotAddingRequestImageLinks(id)
        );
        model.addAttribute("spotAddingRequest", dto);
        return "spot-adding-request-instance";
    }

}
