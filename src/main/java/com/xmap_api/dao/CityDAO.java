package com.xmap_api.dao;

import com.xmap_api.models.City;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CityDAO {
    private final JdbcClient jdbcClient;

    public CityDAO(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<City> getAll() {
        return jdbcClient.sql("SELECT * FROM city")
                .query(City.class)
                .list();
    }
}
