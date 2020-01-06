package com.diploma.backend.service;

import com.diploma.backend.model.entities.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User getUserById(Integer id);

    User getUserByUsername(String username);

    List<User> getAllUsers();
}
