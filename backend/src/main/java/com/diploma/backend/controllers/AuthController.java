package com.diploma.backend.controllers;

import com.diploma.backend.config.WebSecurityConfig;
import com.diploma.backend.model.dto.ApiResponse;
import com.diploma.backend.model.dto.JwtAuthenticationResponse;
import com.diploma.backend.model.dto.UserDTO;
import com.diploma.backend.model.dto.UserLoginDTO;
import com.diploma.backend.model.entities.User;
import com.diploma.backend.service.AccountService;
import com.diploma.backend.service.UserService;
import com.diploma.backend.validation.Groups;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = {"AuthController"})
@Validated
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@ConditionalOnBean(WebSecurityConfig.class)
public class AuthController {

    private final ConversionService conversionService;
    private final AccountService accountService;
    private final ModelMapper mapper;
    private final UserService userService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public JwtAuthenticationResponse authenticateUser(@Validated @RequestBody UserLoginDTO loginRequest) {

        log.debug("Trying to sign in");

        return accountService.login(loginRequest);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse registerUser(@Validated(Groups.CREATE.class) @RequestBody UserDTO userDTO) {

        log.debug("Trying to create user {}", userDTO);
        // Creating user's account
        User user = conversionService.convert(userDTO, User.class);
        userService.createUser(user);

        return new ApiResponse(true, "User registered successfully");
    }

}
