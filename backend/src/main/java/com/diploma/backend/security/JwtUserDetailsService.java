package com.diploma.backend.security;

import com.diploma.backend.config.WebSecurityConfig;
import com.diploma.backend.model.entities.User;
import com.diploma.backend.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

@Service
@ConditionalOnBean(WebSecurityConfig.class)
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
