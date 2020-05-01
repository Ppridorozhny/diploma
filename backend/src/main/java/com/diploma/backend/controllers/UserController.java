package com.diploma.backend.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diploma.backend.AppConstants;
import com.diploma.backend.model.dto.UserShortDTO;
import com.diploma.backend.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
    public List<UserShortDTO> getAllUsers() {
        log.debug("Get all users");
        return mapper.map(userService.getAllUsers(), AppConstants.USER_SHORT_LIST_TYPE);
    }

    @GetMapping("/{userId}")
    public UserShortDTO getUserById(@PathVariable Integer userId) {
        log.debug("Get user with userId {}", userId);
        return mapper.map(userService.getUserById(userId), UserShortDTO.class);
    }

}
