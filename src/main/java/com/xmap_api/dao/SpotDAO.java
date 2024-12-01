package com.xmap_api.dao;

import com.xmap_api.dao.mappers.DefaultSpotRowMapper;
import com.xmap_api.dto.request.NewSpotDTO;
import com.xmap_api.dto.response.DefaultSpotDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class SpotDAO {
    private final JdbcTemplate jdbcTemplate;
    private final DefaultSpotRowMapper defaultSpotRowMapper = new DefaultSpotRowMapper();

    public SpotDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<DefaultSpotDTO> findAllDefaultSpots() {
        return jdbcTemplate.query("""
                  SELECT id, name, lat, lon, inserted_at, updated_at, description
                    FROM spot
              """, defaultSpotRowMapper);
    }

    public DefaultSpotDTO findById(UUID spotId) {
        return jdbcTemplate.query("""
                  SELECT id, name, lat, lon, inserted_at, updated_at, description
                    FROM spot
                   WHERE id = ?
              """, defaultSpotRowMapper, spotId).getFirst();
    }

    public UUID addNewSpot(NewSpotDTO spot) {
        return jdbcTemplate.queryForObject("""
                  INSERT INTO spot (name, lat, lon, inserted_at, updated_at, description)
                       VALUES (?, ?, ?, DEFAULT, DEFAULT, ?)
                    RETURNING id
              """, UUID.class, spot.name(), spot.latitude(), spot.longitude(), spot.description());
    }
}
