package com.xmap_api.controllers;

import com.xmap_api.dto.inside.DownloadedFileDTO;
import com.xmap_api.services.S3FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/s3-file")
public class S3FileController {

    private final S3FileService s3FileService;

    public S3FileController(S3FileService s3FileService) {
        this.s3FileService = s3FileService;
    }

    //TODO добавить сваггер
    //@PostMapping("/{fileType}")
    //public ResponseEntity<S3FileIdResponse> createS3File(@RequestParam("file") MultipartFile file,
    //                                                     @PathVariable("fileType") DBCode.S3File.FileType fileType) {
    //    return ResponseEntity.ok(new S3FileIdResponse(s3FileService.createS3File(file, fileType)));
    //}

    @PostMapping("/spot-image/{spotId}")
    public ResponseEntity<Void> addSpotImage(@RequestParam("file") MultipartFile file,
                                             @PathVariable("spotId") UUID spotId) {
        s3FileService.createSpotImage(file, spotId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/spot-image/{spotId}")
    public ResponseEntity<Resource> getSpotImage(@PathVariable("spotId") UUID spotId) {
        DownloadedFileDTO fileDTO = s3FileService.downloadFile(spotId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, fileDTO.returnedFilename());
        headers.add(HttpHeaders.CONTENT_TYPE, fileDTO.contentType());
        return ResponseEntity.ok().headers(headers).body(new ByteArrayResource(fileDTO.content()));
    }
}
