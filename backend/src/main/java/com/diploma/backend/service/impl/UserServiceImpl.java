package com.diploma.backend.service.impl;

import com.diploma.backend.model.entities.User;
import com.diploma.backend.repository.UserRepository;
import com.diploma.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User getUser(Integer id) {
        return userRepository.getOne(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
