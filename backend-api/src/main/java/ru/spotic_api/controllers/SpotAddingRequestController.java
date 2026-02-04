package ru.spotic_api.controllers;

import ru.spotic_api.dto.request.NewSpotAddingRequestDTO;
import ru.spotic_api.dto.thymeleaf_model.SpotAddingRequestWithImageLinksDTO;
import ru.spotic_api.models.status.SpotAddingRequestStatus;
import ru.spotic_api.security.SecurityUtil;
import ru.spotic_api.services.S3FileService;
import ru.spotic_api.services.SpotAddingRequestService;
import ru.spotic_api.util.DBCode;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class SpotAddingRequestController {

    private final String baseUrl;
    private final List<String> allowedContentTypes = List.of(MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE);
    private final SpotAddingRequestService spotAddingRequestService;
    private final S3FileService s3FileService;

    public SpotAddingRequestController(@Value("${xmap-api.base-url}") String baseUrl,
                                       SpotAddingRequestService spotAddingRequestService, S3FileService s3FileService) {
        this.baseUrl = baseUrl;
        this.spotAddingRequestService = spotAddingRequestService;
        this.s3FileService = s3FileService;
    }

    @GetMapping("/spot-adding-request")
    public String getCreatingPage(Model model) {
        //Такие значения для координат, чтобы они не принялись при создании/
        //Некрасиво, но пока так
        model.addAttribute("formData",
                new NewSpotAddingRequestDTO(null, -91.0, -181.0, null, new ArrayList<>(0)));
        model.addAttribute("activePage", "add-spot");
        return "spot-adding-request";
    }

    @PostMapping("/spot-adding-request")
    public String create(@AuthenticationPrincipal UserDetails userDetails,
                         @Valid @ModelAttribute("formData") NewSpotAddingRequestDTO formData,
                         BindingResult bindingResult,
                         Model model) {

        if (bindingResult.hasErrors()) {
            return "spot-adding-request";
        }
        List<String> fileTypes = formData.files().stream().map(MultipartFile::getContentType)
                .filter(f -> !allowedContentTypes.contains(f)).toList();
        if (!fileTypes.isEmpty()) {
            bindingResult.rejectValue("files", null,
                    "Тип " + fileTypes.getFirst().replace("image/", "") + " не поддерживается");
            return "spot-adding-request";
        }

        UUID spotAddingRequestId = spotAddingRequestService.create(formData, userDetails.getUsername());
        return "redirect:/spot-adding-request/" + spotAddingRequestId;
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
        model.addAttribute("baseUrl", baseUrl);
        model.addAttribute("spotAddingRequest", dto);
        model.addAttribute("spotAddingRequestId", id.toString());
        return "spot-adding-request-instance";
    }
}
