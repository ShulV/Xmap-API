package ru.spotic_api.dao.mappers;

import ru.spotic_api.models.City;
import ru.spotic_api.models.Spot;
import ru.spotic_api.models.SpotAddingRequest;
import ru.spotic_api.models.User;
import ru.spotic_api.models.status.SpotAddingRequestStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SpotAddingRequestRowMapper implements RowMapper<SpotAddingRequest> {

    @Override
    public SpotAddingRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
        String acceptorId = rs.getString("acceptor_id");
        String spotId = rs.getString("spot_id");
        return SpotAddingRequest.builder()
                .id(UUID.fromString(rs.getString("id")))
                .spotName(rs.getString("spot_name"))
                .spotLatitude(rs.getDouble("spot_lat"))
                .spotLongitude(rs.getDouble("spot_lon"))
                .status(SpotAddingRequestStatus.valueOf(rs.getString("status")))
                .insertedAt(rs.getTimestamp("inserted_at"))
                .acceptedAt(rs.getTimestamp("accepted_at"))
                .spotDescription(rs.getString("spot_description"))
                .comment(rs.getString("comment"))
                .adder(new User(UUID.fromString(rs.getString("adder_id"))))
                .acceptor(acceptorId == null ? null : new User(UUID.fromString(acceptorId)))
                .city(new City(rs.getLong("city_id"), rs.getString("city_name")))
                .spot(spotId == null ? null : new Spot(UUID.fromString(spotId)))
                .build();
    }
}
