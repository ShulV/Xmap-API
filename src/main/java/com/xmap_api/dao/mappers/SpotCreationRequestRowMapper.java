package com.xmap_api.dao.mappers;

import com.xmap_api.models.SpotCreationRequest;
import com.xmap_api.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SpotCreationRequestRowMapper implements RowMapper<SpotCreationRequest> {

    @Override
    public SpotCreationRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
        String acceptorId = rs.getString("acceptor_id");
        return SpotCreationRequest.builder()
                .id(UUID.fromString(rs.getString("id")))
                .spotName(rs.getString("spot_name"))
                .spotLatitude(rs.getDouble("spot_lat"))
                .spotLongitude(rs.getDouble("spot_lon"))
                .insertedAt(rs.getTimestamp("inserted_at"))
                .acceptedAt(rs.getTimestamp("accepted_at"))
                .spotDescription(rs.getString("spot_description"))
                .comment(rs.getString("comment"))
                .creator(new User(UUID.fromString(rs.getString("creator_id"))))
                .acceptor(acceptorId == null ? null : new User(UUID.fromString(acceptorId)))
                .build();
    }
}
