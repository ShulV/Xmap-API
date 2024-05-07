package com.xmap_api.probe;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProbeService {
    private final ProbeRepo probeRepo;

    public ProbeService(ProbeRepo probeRepo) {
        this.probeRepo = probeRepo;
    }

    public List<ProbeEntity> findAll() {
        return (List<ProbeEntity>) probeRepo.findAll();
    }
}
