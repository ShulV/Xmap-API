package com.xmap_api.services;

import com.xmap_api.models.Spot;
import com.xmap_api.repos.SpotRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpotService {
    private final SpotRepo spotRepo;

    public SpotService(SpotRepo spotRepo) {
        this.spotRepo = spotRepo;
    }

    public List<Spot> getAll() {
        return spotRepo.findAll();
    }
}
