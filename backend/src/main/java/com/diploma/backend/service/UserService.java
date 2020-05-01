package com.diploma.backend.service;

import java.util.List;

import com.diploma.backend.model.entities.User;

public interface UserService {

    User createUser(User user);

    User getUserById(Integer id);

    User getUserByUsername(String username);

    List<User> getAllUsers();

}
