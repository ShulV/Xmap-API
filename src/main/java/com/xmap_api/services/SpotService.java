package com.xmap_api.services;

import com.xmap_api.dao.SpotDAO;
import com.xmap_api.dto.request.NewSpotDTO;
import com.xmap_api.dto.response.DefaultSpotDTO;
import com.xmap_api.dto.thymeleaf_model.SpotWithImageLinksDTO;
import com.xmap_api.exceptions.XmapApiException;
import jakarta.transaction.Transactional;
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

    private final SpotDAO spotDAO;
    private final S3FileService s3FileService;

    public SpotService(SpotDAO spotDAO, S3FileService s3FileService) {
        this.spotDAO = spotDAO;
        this.s3FileService = s3FileService;
    }

    public Page<DefaultSpotDTO> getDefaultAll(Pageable pageable) {
//        log.info("Getting all default spots with pagination: [page = '{}', size = '{}']",
//                pageable.getPageNumber(), pageable.getPageSize());//пока нагрузки нет, пусть логируется
        return spotDAO.findAllDefaultSpots(pageable);
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

    @Transactional
    public UUID createSpotForModeration(NewSpotDTO dto, List<MultipartFile> files) {
        UUID newSpotId = spotDAO.addNewSpot(dto);
        s3FileService.createSpotImages(files, newSpotId);
        return newSpotId;
    }
}
