package com.xmap_api.dao.mappers;

import com.xmap_api.dto.response.DefaultSpotDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class DefaultSpotRowMapper implements RowMapper<DefaultSpotDTO> {
    @Override
    public DefaultSpotDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return DefaultSpotDTO.builder()
                .id(UUID.fromString(rs.getString("id")))
                .name(rs.getString("name"))
                .latitude(rs.getDouble("lat"))
                .longitude(rs.getDouble("lon"))
                .insertedAt(new SimpleDateFormat("yyyy-MM-dd").format(
                        rs.getTimestamp("inserted_at")))
                .updatedAt(new SimpleDateFormat("yyyy-MM-dd").format(
                        rs.getTimestamp("updated_at")))
                .description(rs.getString("description"))
                .city(rs.getString("city"))
                .build();
    }
}
