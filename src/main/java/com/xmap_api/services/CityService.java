package com.xmap_api.services;

import com.xmap_api.dao.CityDAO;
import com.xmap_api.models.City;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    private final CityDAO cityDAO;

    public CityService(CityDAO cityDAO) {
        this.cityDAO = cityDAO;
    }

    public List<City> getBySubstring(String substring) {
        return cityDAO.getBySubstring(substring);
    }

    @NonNull
    public City getById(Long id) {
        return cityDAO.getById(id);
    }

    @Nullable
    public City getByName(String name) {
        return cityDAO.getByName(name);
    }
}
