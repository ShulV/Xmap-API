package com.xmap_api.services;

import com.xmap_api.dao.SpotDAO;
import com.xmap_api.dto.request.NewSpotDTO;
import com.xmap_api.dto.response.DefaultSpotDTO;
import com.xmap_api.dto.response.SpotInfoForMapDTO;
import com.xmap_api.dto.response.SpotInfoForMapDialogDTO;
import com.xmap_api.dto.thymeleaf_model.MinSpot;
import com.xmap_api.dto.thymeleaf_model.SpotWithImageLinksDTO;
import com.xmap_api.exceptions.XmapApiException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

//import static com.xmap_api.config.LogbackConfig.Logger.SPOT_LOG;

@Service
public class SpotService {
//    private final Logger log = LoggerFactory.getLogger(SPOT_LOG);


    private final String s3FileDownloadLinkTemplate;
    private final String s3FileDownloadLinkPathParam;

    private final SpotDAO spotDAO;
    private final S3FileService s3FileService;

    public SpotService(@Value("${xmap-api.s3-file.download-link-template}") String s3FileDownloadLinkTemplate,
                       @Value("${xmap-api.s3-file.download-link-path-param}") String s3FileDownloadLinkPathParam,
                       SpotDAO spotDAO, S3FileService s3FileService) {
        this.s3FileDownloadLinkTemplate = s3FileDownloadLinkTemplate;
        this.s3FileDownloadLinkPathParam = s3FileDownloadLinkPathParam;
        this.spotDAO = spotDAO;
        this.s3FileService = s3FileService;
    }

    public Page<DefaultSpotDTO> getDefaultAll(Pageable pageable) {
//        log.info("Getting all default spots with pagination: [page = '{}', size = '{}']",
//                pageable.getPageNumber(), pageable.getPageSize());//пока нагрузки нет, пусть логируется
        return spotDAO.findAllDefaultSpots(pageable);
    }

    public Page<MinSpot> getWithFirstImage(Pageable pageable) {
        return spotDAO.getWithFirstImage(pageable, s3FileDownloadLinkTemplate, s3FileDownloadLinkPathParam);
    }

    public SpotInfoForMapDialogDTO getWithFirstImage(UUID spotId) {
        return spotDAO.getWithFirstImage(spotId, s3FileDownloadLinkTemplate, s3FileDownloadLinkPathParam);
    }

    public SpotWithImageLinksDTO getSpotWithImageLinks(UUID spotId) {
        try {
            DefaultSpotDTO defaultSpotDTO = spotDAO.findById(spotId);
            List<String> spotImageLinks = s3FileService.getSpotImageLinks(spotId);
            return new SpotWithImageLinksDTO(defaultSpotDTO, spotImageLinks);
        } catch (NoSuchElementException e) {
//            log.error("No default spot found with: [id ='{}']", spotId);
            throw new XmapApiException();
        }
    }

    public List<SpotInfoForMapDTO> getMinSpotInfoForMap(Long cityId, Double locationLat, Double locationLon,
                                                        Long distance) {
        return spotDAO.getMinSpotInfoForMap(cityId, locationLat, locationLon, distance);
    }

    @Transactional
    public UUID createSpotForModeration(NewSpotDTO dto, List<MultipartFile> files) {
        UUID newSpotId = spotDAO.addNewSpot(dto);
        s3FileService.createSpotImages(files, newSpotId);
        return newSpotId;
    }

    @Transactional
    public UUID createSpotByAddingRequest(UUID spotAddingRequestId) {
        UUID newSpotId = spotDAO.createSpotByAddingRequest(spotAddingRequestId);
        s3FileService.linkSpotToImages(newSpotId, spotAddingRequestId);
        return newSpotId;
    }
}
