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

    public List<City> getBySubstring(String substring) {
        return jdbcClient.sql("SELECT * FROM city WHERE (:substring IS NULL OR LOWER(name) LIKE LOWER(:substring))")
                .param("substring", substring == null || substring.isBlank() ? null : substring + "%")
                .query(City.class)
                .list();
    }

    public City getById(Long id) {
        return jdbcClient.sql("SELECT * FROM city WHERE id = :id")
                .param("id", id)
                .query(City.class)
                .single();
    }
}
