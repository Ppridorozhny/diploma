package com.diploma.backend.security;

import java.util.Optional;
import java.util.function.Function;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diploma.backend.model.entities.User;
import com.diploma.backend.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String usernameOrEmail) {
        return loadUser(usernameOrEmail, userRepository::findByUsernameOrEmail,
                "User not found with username or email : " + usernameOrEmail);
    }

    @Transactional(readOnly = true)
    public UserDetails loadUserById(Integer id) {
        return loadUser(id, userRepository::findById, "User not found with id : " + id);
    }

    private <T> UserDetails loadUser(T searchParameter, Function<T, Optional<User>> function, String errorMessage) {
        User user = function.apply(searchParameter).orElseThrow(
                () -> new UsernameNotFoundException(errorMessage)
        );
        return UserPrincipal.create(user);
    }

}
