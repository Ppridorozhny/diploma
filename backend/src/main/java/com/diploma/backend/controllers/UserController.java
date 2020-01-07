package com.diploma.backend.controllers;

import com.diploma.backend.model.dto.UserDTO;
import com.diploma.backend.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.diploma.backend.AppConstants.USER_LIST_TYPE;

@Api(tags = {"UserController"})
@Validated
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserController {

    private final UserService userService;
    private final ModelMapper mapper;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        log.debug("Get all users");
        return mapper.map(userService.getAllUsers(), USER_LIST_TYPE);
    }

    @GetMapping("/{username}")
    public UserDTO getUserByUsername(@PathVariable String username) {
        log.debug("Get user with username {}", username);
        return mapper.map(userService.getUserByUsername(username), UserDTO.class);
    }

}
