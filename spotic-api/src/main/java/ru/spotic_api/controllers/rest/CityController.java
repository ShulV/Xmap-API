package ru.spotic_api.controllers.rest;

import ru.spotic_api.dto.response.CityDTO;
import ru.spotic_api.models.City;
import ru.spotic_api.services.CityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/api/city/{id}")
    public CityDTO getById(@PathVariable Long id) {
        City city = cityService.getById(id);
        return new CityDTO(city.getId(), city.getName());
    }

    @GetMapping("/api/city")
    public CityDTO getById(@RequestParam String name) {
        City city = cityService.getByName(name);
        return city != null ? new CityDTO(city.getId(), city.getName()) : null;
    }
}
