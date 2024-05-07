package com.xmap_api.probe;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class ProbeService {
    private final ProbeRepo probeRepo;

    public ProbeService(ProbeRepo probeRepo) {
        this.probeRepo = probeRepo;
    }

    public List<ProbeEntity> findAll() {
        return (List<ProbeEntity>) probeRepo.findAll();
    }
    public ProbeEntity findById(int id) {
        Optional<ProbeEntity> probeEntityOpt = probeRepo.findById(id);
        if (probeEntityOpt.isPresent()) {
            return probeEntityOpt.get();
        } else {
            log.info("Probe not found: [id = '{}']", id);
            return null;
        }
    }
}
