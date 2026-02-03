package ru.spotic_api.dao;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserDAO {
    private final JdbcClient jdbcClient;

    public UserDAO(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public UUID getId(String username) {
        return jdbcClient.sql("SELECT id FROM \"user\" WHERE username = :username")
                .param("username", username)
                .query(UUID.class)
                .single();
    }
}
