package com.xmap_api.controllers;

import com.xmap_api.dto.request.NewSpotDTO;
import com.xmap_api.dto.response.DefaultSpotDTO;
import com.xmap_api.dto.response.SpotIdDTO;
import com.xmap_api.dto.response.SpotWithImageLinksDTO;
import com.xmap_api.services.SpotService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/spot")
public class SpotController {
    private final SpotService spotService;

    public SpotController(SpotService spotService) {
        this.spotService = spotService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<DefaultSpotDTO>> getAll() {
        return ResponseEntity.ok(spotService.getDefaultAll());
    }

    @GetMapping("/{spotId}")
    public ResponseEntity<SpotWithImageLinksDTO> getSpotInfo(@PathVariable("spotId") UUID spotId) {
        return ResponseEntity.ok(spotService.getSpotWithImageLinks(spotId));
    }

    // Создание спота с картинкам для дальнейшей модерации
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> createSpotForModeration(@RequestPart("spot") NewSpotDTO dto,
                                                     @RequestPart("files") List<MultipartFile> files) {
        return ResponseEntity.ok(new SpotIdDTO(spotService.createSpotForModeration(dto, files)));
    }
}
