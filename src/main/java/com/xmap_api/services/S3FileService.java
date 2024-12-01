package com.xmap_api.services;

import com.xmap_api.dao.S3FileDAO;
import com.xmap_api.dto.inside.DownloadedFileDTO;
import com.xmap_api.models.S3File;
import com.xmap_api.util.DBCode;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class S3FileService {
    @Value("${xmap-api.spot-image.download-urn-template}")
    private String spotDownloadUrnTemplate;
    @Value("${xmap-api.spot-image.download-urn-path-param}")
    private String downloadUrnPathParam;

    private final S3FileDAO s3FileDAO;
    private final SpotS3FileService spotS3FileService;

    public S3FileService(S3FileDAO s3FileDAO, SpotS3FileService spotS3FileService) {
        this.s3FileDAO = s3FileDAO;
        this.spotS3FileService = spotS3FileService;
    }

    //@Transactional
    //public UUID createS3File(MultipartFile file, DBCode.S3File.FileType fileType) {
    //    return s3FileDAO.insert(new S3File(file, fileType));
    //}

    @Transactional
    public void createSpotImage(MultipartFile file, UUID spotId) {
        UUID s3FileId =  s3FileDAO.insert(new S3File(file, DBCode.S3File.FileType.SPOT_IMAGE));
        spotS3FileService.insert(spotId, s3FileId);
    }

    @Transactional
    public void createSpotImages(MultipartFile[] files, UUID spotId) {
        List<S3File> s3Files = Arrays.stream(files)
                .map(file -> new S3File(file, DBCode.S3File.FileType.SPOT_IMAGE))
                .toList();
        s3FileDAO.batchInsertAndLink(s3Files, spotId);
    }

    public DownloadedFileDTO downloadFile(UUID s3FileId) {
        return s3FileDAO.getForDownloading(s3FileId);
    }

    public List<String> getSpotImageLinks(UUID spotId) {
        return s3FileDAO.getSpotImageLinks(spotId).stream()
                .map(s3FileId -> spotDownloadUrnTemplate.replace(downloadUrnPathParam, s3FileId.toString()))
                .toList();
    }
}
