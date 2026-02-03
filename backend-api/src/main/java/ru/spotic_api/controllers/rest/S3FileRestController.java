package ru.spotic_api.controllers.rest;

import ru.spotic_api.dto.inside.DownloadedFileDTO;
import ru.spotic_api.services.S3FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/s3-file")
public class S3FileRestController {

    private final S3FileService s3FileService;

    public S3FileRestController(S3FileService s3FileService) {
        this.s3FileService = s3FileService;
    }

    @PostMapping("/spot-image/{spotId}")
    public ResponseEntity<Void> addSpotImage(@RequestParam("file") MultipartFile file,
                                             @PathVariable("spotId") UUID spotId) {
        s3FileService.createSpotImage(file, spotId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{s3FileId}")
    public ResponseEntity<Resource> getImage(@PathVariable("s3FileId") UUID s3FileId) {
        DownloadedFileDTO fileDTO = s3FileService.downloadFile(s3FileId);
        HttpHeaders headers = new HttpHeaders();
        //так файл скачается, а нам это пока не надо
        //headers.add(HttpHeaders.CONTENT_DISPOSITION, fileDTO.returnedFilename());
        headers.add(HttpHeaders.CONTENT_TYPE, fileDTO.contentType());
        return ResponseEntity.ok().headers(headers).body(new ByteArrayResource(fileDTO.content()));
    }
}
