package com.xmap_api.services;

import com.xmap_api.dao.UserDAO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UUID getId(String username) {
        return userDAO.getId(username);
    }
}
