package com.xmap_api.rest_controllers;

import com.xmap_api.dto.request.NewSpotDTO;
import com.xmap_api.dto.response.DefaultSpotDTO;
import com.xmap_api.dto.response.SpotIdDTO;
import com.xmap_api.dto.response.SpotWithImageLinksDTO;
import com.xmap_api.services.SpotService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/spot")
public class SpotRestController {
    private final SpotService spotService;

    public SpotRestController(SpotService spotService) {
        this.spotService = spotService;
    }

    @GetMapping("/all")
    public ResponseEntity<Page<DefaultSpotDTO>> getAll(@RequestParam(defaultValue = "0", value = "page") int page,
                                                       @RequestParam(defaultValue = "10", value = "size") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<DefaultSpotDTO> pagedResult = spotService.getDefaultAll(paging);

        if (pagedResult.hasContent()) {
            return ResponseEntity.ok().body(pagedResult);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
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

    //метод для модерации имеет смысл делать, когда будет какая-либо авторизация
}
