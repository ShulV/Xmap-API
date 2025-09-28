package com.xmap_api.controllers.rest;

import com.xmap_api.dto.request.SpotFilterDTO;
import com.xmap_api.dto.response.SpotInfoForMapDTO;
import com.xmap_api.dto.response.SpotInfoForMapDialogDTO;
import com.xmap_api.services.SpotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class SpotRestController {
    private final SpotService spotService;

    public SpotRestController(SpotService spotService) {
        this.spotService = spotService;
    }

    @PostMapping("/api/spot/list/for-map")
    public ResponseEntity<List<SpotInfoForMapDTO>> getMinSpotInfoListForMap(@RequestBody SpotFilterDTO filter) {
        List<SpotInfoForMapDTO> minSpotInfoList = spotService.getMinSpotInfoForMap(filter.cityId(),
                filter.locationLat(), filter.locationLon(), filter.radius());
        return ResponseEntity.ok().body(minSpotInfoList);
    }

    @GetMapping("/api/spot/{id}/for-map")
    public ResponseEntity<SpotInfoForMapDialogDTO> getSpotInfoForMap(@PathVariable UUID id,
                                                                     @RequestParam Double locationLon,
                                                                     @RequestParam Double locationLat) {
        return ResponseEntity.ok().body(spotService.getWithFirstImage(id, locationLon, locationLat));
    }

//    @GetMapping("/api/spot/all")
//    public ResponseEntity<Page<DefaultSpotDTO>> getAll(@RequestParam(defaultValue = "0", value = "page") int page,
//                                                       @RequestParam(defaultValue = "10", value = "size") int size) {
//        Pageable paging = PageRequest.of(page, size);
//        Page<DefaultSpotDTO> pagedResult = spotService.getDefaultAll(paging);
//
//        if (pagedResult.hasContent()) {
//            return ResponseEntity.ok().body(pagedResult);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//    }

//    @GetMapping("/api/spot/{spotId}")
//    public ResponseEntity<SpotWithImageLinksDTO> getSpotInfo(@PathVariable("spotId") UUID spotId) {
//        return ResponseEntity.ok(spotService.getSpotWithImageLinks(spotId));
//    }

    // Создание спота с картинкам для дальнейшей модерации
//    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<?> createSpotForModeration(@RequestPart("spot") NewSpotDTO dto,
//                                                     @RequestPart("files") List<MultipartFile> files) {
//        return ResponseEntity.ok(new SpotIdDTO(spotService.createSpotForModeration(dto, files)));
//    }

    //метод для модерации имеет смысл делать, когда будет какая-либо авторизация
}
