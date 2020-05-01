package com.diploma.backend.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diploma.backend.model.enums.Priority;
import com.diploma.backend.model.enums.RelationType;
import com.diploma.backend.model.enums.Resolution;
import com.diploma.backend.model.enums.TicketType;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(tags = {"EnvironmentController"})
@Validated
@RestController
@RequestMapping("/api/environment")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class EnvironmentController {

    @GetMapping("/priorities")
    public List<String> getPriorities() {
        return Arrays.stream(Priority.values())
                .map(Enum::toString)
                .collect(Collectors.toList());
    }

    @GetMapping("/relation-type")
    public List<String> getRelationTypes() {
        return Arrays.stream(RelationType.values())
                .map(Enum::toString)
                .collect(Collectors.toList());
    }

    @GetMapping("/resolutions")
    public List<String> getResolutions() {
        return Arrays.stream(Resolution.values())
                .map(Enum::toString)
                .collect(Collectors.toList());
    }

    @GetMapping("/ticket-type")
    public List<String> getTicketTypes() {
        return Arrays.stream(TicketType.values())
                .map(Enum::toString)
                .collect(Collectors.toList());
    }

}
