package com.diploma.backend.controllers;

import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diploma.backend.AppConstants;
import com.diploma.backend.model.dto.RunInfoDTO;
import com.diploma.backend.model.dto.StatisticEntryDTO;
import com.diploma.backend.model.entities.DefectStatisticEntry;
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
    public List<StatisticEntryDTO> getUserStatistic(@RequestParam Integer projectId) {
        log.debug("Get user statistic for project with id {}", projectId);
        return mapper.map(analyticService.getUserStatisticsByProjectId(projectId),
                AppConstants.STATISTIC_LIST_TYPE);
    }

    @GetMapping("/ticket-type-statistics")
    public List<StatisticEntryDTO> getTicketTypeStatistics(@RequestParam Integer projectId) {
        log.debug("Get ticket type for project with id {}", projectId);
        return mapper.map(analyticService.getTicketTypeStatisticsByProjectId(projectId),
                AppConstants.TICKET_TYPE_STATISTIC_LIST_TYPE);
    }

    @GetMapping("/defects-statistics")
    public Map<String, DefectStatisticEntry> getDefectStatistic(@RequestParam Integer projectId) {
        log.debug("Get defects statistic for project with id {}", projectId);
        return analyticService.getDefectsStatisticByProjectId(projectId);
    }

    @GetMapping("/run-info-statistics")
    public List<RunInfoDTO> getRunInfoStatistic(@RequestParam String seriesId) {
        log.debug("Get run ifo for series with id {}", seriesId);
        return mapper.map(analyticService.getRunStatistic(seriesId),
                AppConstants.RUN_INFO_STATISTIC_LIST_TYPE);
    }

}
