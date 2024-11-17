package com.xmap_api.services;

import com.xmap_api.dao.SpotDAO;
import com.xmap_api.models.Spot;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpotService {
    private final SpotDAO spotDAO;

    public SpotService(SpotDAO spotDAO) {
        this.spotDAO = spotDAO;
    }

    public List<Spot> getAll() {
        return spotDAO.findAll();
    }
}
