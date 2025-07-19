package com.xmap_api.dao;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {
    private final JdbcClient jdbcClient;

    public UserDAO(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }
}
