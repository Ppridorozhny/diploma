package com.diploma.backend.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diploma.backend.AppConstants;
import com.diploma.backend.model.dto.UserStatisticDTO;
import com.diploma.backend.model.entities.UserStatistic;
import com.diploma.backend.service.AnalyticService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(tags = {"AnalyticController"})
@Validated
@RestController
@RequestMapping("/api/analytic")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AnalyticController {

    private final AnalyticService analyticService;
    private final ModelMapper mapper;

    @GetMapping("/user-statistics")
    public List<UserStatisticDTO> getAvailableStatuses(@RequestParam Integer projectId) {
        log.debug("Get available statuses for project with id {}", projectId);
        final List<UserStatistic> userStatisticsByProjectId = analyticService.getUserStatisticsByProjectId(projectId);
        return mapper.map(userStatisticsByProjectId,
                AppConstants.USER_STATISTIC_LIST_TYPE);
    }

}
