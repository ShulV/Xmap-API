package ru.spotic_api.services;

import ru.spotic_api.dao.UserDAO;
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
