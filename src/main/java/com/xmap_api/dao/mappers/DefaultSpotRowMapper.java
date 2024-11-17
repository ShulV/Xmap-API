package com.xmap_api.dao.mappers;

import com.xmap_api.models.Spot;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DefaultSpotRowMapper implements RowMapper<Spot> {
    @Override
    public Spot mapRow(ResultSet rs, int rowNum) throws SQLException {
        Spot spot = new Spot();
        spot.setId(UUID.fromString(rs.getString("id")));
        spot.setName(rs.getString("name"));
        spot.setLatitude(rs.getDouble("lat"));
        spot.setLongitude(rs.getDouble("lon"));
        spot.setAccepted(rs.getBoolean("accepted"));
        spot.setInsertedAt(rs.getTimestamp("inserted_at"));
        spot.setUpdatedAt(rs.getTimestamp("updated_at"));
        spot.setDescription(rs.getString("description"));
        return spot;
    }
}
