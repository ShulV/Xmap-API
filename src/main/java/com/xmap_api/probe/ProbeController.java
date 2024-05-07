package com.xmap_api.probe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProbeController {

    private final ProbeService probeService;

    Logger logger = LoggerFactory.getLogger(ProbeController.class);
    public ProbeController(ProbeService probeService) {
        this.probeService = probeService;
    }

    @GetMapping("/probe")
    public String probe() {
        return "probe...";
    }

    @GetMapping("/probe/all")
    public List<ProbeEntity> getAll() {
        //        trace  debug info warn error
        logger.info("logger success test");
        logger.error("logger error test");

        return probeService.findAll();
    }
}
