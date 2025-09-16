package com.xmap_api.services;

import com.xmap_api.dao.CityDAO;
import com.xmap_api.models.City;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    private final CityDAO cityDAO;

    public CityService(CityDAO cityDAO) {
        this.cityDAO = cityDAO;
    }

    public List<City> getAll() {
        return cityDAO.getAll();
    }
}
