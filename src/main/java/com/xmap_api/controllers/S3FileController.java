package com.xmap_api.controllers;

import com.xmap_api.dto.response.S3FileIdResponse;
import com.xmap_api.services.S3FileService;
import com.xmap_api.util.DBCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/s3-file")
public class S3FileController {

    private final S3FileService s3FileService;

    public S3FileController(S3FileService s3FileService) {
        this.s3FileService = s3FileService;
    }

    //TODO добавить сваггер
    @PostMapping("/{fileType}")
    public ResponseEntity<S3FileIdResponse> createS3File(@RequestParam("file") MultipartFile file,
                                                         @PathVariable("fileType") DBCode.S3File.FileType fileType) {
        return ResponseEntity.ok(new S3FileIdResponse(s3FileService.createS3File(file, fileType)));
    }
}
