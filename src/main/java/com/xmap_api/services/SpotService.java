package com.xmap_api.services;

import com.xmap_api.dao.SpotDAO;
import com.xmap_api.dto.response.DefaultSpotDTO;
import com.xmap_api.dto.response.SpotWithImageLinksDTO;
import com.xmap_api.exceptions.XmapApiException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class SpotService {
    private final SpotDAO spotDAO;
    private final S3FileService s3FileService;

    public SpotService(SpotDAO spotDAO, S3FileService s3FileService) {
        this.spotDAO = spotDAO;
        this.s3FileService = s3FileService;
    }

    public List<DefaultSpotDTO> getDefaultAll() {
        return spotDAO.findAllDefaultSpots();
    }

    public SpotWithImageLinksDTO getSpotWithImageLinks(UUID spotId) {
        try {
            DefaultSpotDTO defaultSpotDTO = spotDAO.findById(spotId);
            List<String> spotImageLinks = s3FileService.getSpotImageLinks(spotId);
            return new SpotWithImageLinksDTO(defaultSpotDTO, spotImageLinks);
        } catch (NoSuchElementException e) {
            throw new XmapApiException();
        }
    }
}
