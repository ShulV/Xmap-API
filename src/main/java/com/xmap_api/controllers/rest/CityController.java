package com.xmap_api.controllers.rest;

import com.xmap_api.dto.response.CityDTO;
import com.xmap_api.services.CityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CityController {
    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/api/city/list")
    public List<CityDTO> getBySubstring(@RequestParam(required = false) String substring) {
        return cityService.getBySubstring(substring).stream().map(city -> new CityDTO(city.getId(), city.getName())).toList();
    }
}
