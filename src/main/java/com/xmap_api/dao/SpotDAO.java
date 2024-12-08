package com.xmap_api.dao;

import com.xmap_api.dao.mappers.DefaultSpotRowMapper;
import com.xmap_api.dto.request.NewSpotDTO;
import com.xmap_api.dto.response.DefaultSpotDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class SpotDAO {
    private final JdbcTemplate jdbcTemplate;
    private final JdbcClient jdbcClient;
    private final DefaultSpotRowMapper defaultSpotRowMapper = new DefaultSpotRowMapper();

    public SpotDAO(JdbcTemplate jdbcTemplate, JdbcClient jdbcClient) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcClient = jdbcClient;
    }

    public Page<DefaultSpotDTO> findAllDefaultSpots(Pageable pageable) {
        List<DefaultSpotDTO> content = jdbcClient.sql("""
            SELECT id, name, lat, lon, inserted_at, updated_at, description FROM spot
             LIMIT :limit
            OFFSET :offset
        """)
                .params(Map.of(
                    "limit", pageable.getPageSize(),
                    "offset", pageable.getOffset()))
                .query(defaultSpotRowMapper).list();
        return new PageImpl<>(content, pageable, countTotalElements());
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

    private long countTotalElements() {
        return jdbcClient.sql("SELECT COUNT(*) FROM spot").query(Long.class).single();
    }
}
