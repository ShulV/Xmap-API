package com.xmap_api.probe;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log4j2
public class ProbeController {
    private final ProbeService probeService;

    public ProbeController(ProbeService probeService) {
        this.probeService = probeService;
    }

    @GetMapping("/probe")
    public String probe() {
        log.debug("This is a debug message");
        log.info("This is an info message");
        log.warn("This is a warn message");
        log.error("This is an error message");
        return "probe...";
    }

    @GetMapping("/probe/all")
    public List<ProbeEntity> getAll() {
        log.info("Get all probe");
        return probeService.findAll();
    }

    @GetMapping("/probe/{id}")
    public ProbeEntity getById(@PathVariable int id) {
        log.info("getById: [id = '{}']", id);
        return probeService.findById(id);
    }
}
