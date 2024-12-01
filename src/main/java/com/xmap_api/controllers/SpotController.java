package com.xmap_api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xmap_api.dto.request.NewSpotDTO;
import com.xmap_api.dto.response.DefaultSpotDTO;
import com.xmap_api.dto.response.SpotIdDTO;
import com.xmap_api.dto.response.SpotWithImageLinksDTO;
import com.xmap_api.services.SpotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/spot")
public class SpotController {
    private final SpotService spotService;
    private final ObjectMapper objectMapper;

    public SpotController(SpotService spotService, ObjectMapper objectMapper) {
        this.spotService = spotService;
        this.objectMapper = objectMapper;
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
    @PostMapping
    public ResponseEntity<?> createSpotForModeration(@RequestParam("files") MultipartFile[] files,
                                                     @RequestParam("spot") String spotJson) throws JsonProcessingException {
        NewSpotDTO dto = objectMapper.readValue(spotJson, NewSpotDTO.class);//TODO сразу нельзя?
        return ResponseEntity.ok(new SpotIdDTO(spotService.createSpotForModeration(dto, files)));
    }
}
