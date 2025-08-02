package com.xmap_api.services;

import com.xmap_api.dao.S3FileDAO;
import com.xmap_api.dto.inside.DownloadedFileDTO;
import com.xmap_api.models.S3File;
import com.xmap_api.util.DBCode;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class S3FileService {
    @Value("${xmap-api.s3-file.download-link-template}")
    private String s3FileDownloadLinkTemplate;
    @Value("${xmap-api.s3-file.download-link-path-param}")
    private String s3FileDownloadLinkPathParam;

    private final S3FileDAO s3FileDAO;
    private final SpotS3FileService spotS3FileService;

    public S3FileService(S3FileDAO s3FileDAO, SpotS3FileService spotS3FileService) {
        this.s3FileDAO = s3FileDAO;
        this.spotS3FileService = spotS3FileService;
    }

    @Transactional
    public void createSpotImage(MultipartFile file, UUID spotId) {
        UUID s3FileId =  s3FileDAO.insert(new S3File(file, DBCode.S3File.FileType.SPOT_IMAGE));
        spotS3FileService.insert(spotId, s3FileId);
    }

    @Transactional
    public void createSpotImages(List<MultipartFile> files, UUID spotId) {
        List<S3File> s3Files = files.stream()
                .map(file -> new S3File(file, DBCode.S3File.FileType.SPOT_IMAGE))
                .toList();
        s3FileDAO.batchInsertSpotWithS3Files(s3Files, spotId);
    }

    @Transactional
    public void createSpotAddingRequestImages(List<MultipartFile> files, UUID spotAddingRequestId) {
        List<S3File> s3Files = files.stream()
                .map(file -> new S3File(file, DBCode.S3File.FileType.SPOT_IMAGE))
                .toList();
        s3FileDAO.batchInsertSpotAddingRequestWithS3Files(s3Files, spotAddingRequestId);
    }

    public DownloadedFileDTO downloadFile(UUID s3FileId) {
        return s3FileDAO.getForDownloading(s3FileId);
    }

    public List<String> getSpotImageLinks(UUID spotId) {
        return s3FileDAO.getSpotImageLinks(spotId).stream()
                .map(s3FileId -> s3FileDownloadLinkTemplate.replace(s3FileDownloadLinkPathParam, s3FileId.toString()))
                .toList();
    }

    public List<String> getSpotAddingRequestImageLinks(UUID spotAddingRequestId) {
        return s3FileDAO.getSpotAddingRequestImageLinks(spotAddingRequestId).stream()
                .map(s3FileId -> s3FileDownloadLinkTemplate.replace(s3FileDownloadLinkPathParam, s3FileId.toString()))
                .toList();
    }

    public void linkSpotToImages(UUID spotId, UUID spotCreationRequestId) {
        s3FileDAO.linkSpotToImages(spotId, spotCreationRequestId);
    }
}
