package com.diploma.backend.controllers;

import com.diploma.backend.error.exceptions.ResourceNotFoundException;
import com.diploma.backend.model.dto.ApiResponse;
import com.diploma.backend.model.dto.JwtAuthenticationResponse;
import com.diploma.backend.model.dto.UserDTO;
import com.diploma.backend.model.dto.UserLoginDTO;
import com.diploma.backend.model.entities.User;
import com.diploma.backend.repository.UserRepository;
import com.diploma.backend.security.JwtTokenProvider;
import com.diploma.backend.service.UserService;
import com.diploma.backend.validation.Groups;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@Api(tags = {"AuthController"})
@Validated
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final ConversionService conversionService;
    private final JwtTokenProvider tokenProvider;
    private final ModelMapper mapper;
    private final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Validated @RequestBody UserLoginDTO loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@Validated(Groups.CREATE.class) @RequestBody UserDTO userDTO) {

        // Creating user's account
        User user = conversionService.convert(userDTO, User.class);
        if (user != null) {
            throw new ResourceNotFoundException("dsa", "dsa", "dasd");
        }
        User result = userService.createUser(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

}
