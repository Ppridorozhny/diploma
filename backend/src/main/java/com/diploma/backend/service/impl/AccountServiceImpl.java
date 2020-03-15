package com.diploma.backend.service.impl;

import com.diploma.backend.AppConstants;
import com.diploma.backend.config.WebSecurityConfig;
import com.diploma.backend.model.dto.JwtAuthenticationResponse;
import com.diploma.backend.model.dto.UserLoginDTO;
import com.diploma.backend.security.JwtTokenProvider;
import com.diploma.backend.security.UserPrincipal;
import com.diploma.backend.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@ConditionalOnBean(WebSecurityConfig.class)
public class AccountServiceImpl implements AccountService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @Override
    public JwtAuthenticationResponse login(UserLoginDTO loginDTO) {

        log.debug("Trying to login user with username or email {}", loginDTO.getUsernameOrEmail());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsernameOrEmail(),
                        loginDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = AppConstants.TOKEN_TYPE + tokenProvider.generateToken(authentication);

        log.debug("Created token {}", jwt);

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        log.debug("User {} is signed in", user);

        return new JwtAuthenticationResponse(jwt, user.getFirstName(), user.getLastName(),
                user.getUsername(), user.getEmail());
    }
}
