package com.diploma.backend.service;

import com.diploma.backend.model.entities.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User getUser(Integer id);

    List<User> getAllUsers();
}
