package com.xmap_api.dao;

import com.xmap_api.dao.mappers.DefaultSpotRowMapper;
import com.xmap_api.models.Spot;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SpotDAO {
    private final JdbcTemplate jdbcTemplate;
    private final DefaultSpotRowMapper defaultSpotRowMapper = new DefaultSpotRowMapper();

    public SpotDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Spot> findAll() {
        return jdbcTemplate.query("SELECT * FROM spot", defaultSpotRowMapper);
    }
}
