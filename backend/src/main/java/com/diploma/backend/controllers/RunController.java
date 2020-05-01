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
import com.diploma.backend.model.dto.RunDTO;
import com.diploma.backend.service.RunService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/api/runs")
@Api(tags = {"RunController"})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RunController {

    private final RunService runService;
    private final ModelMapper mapper;

    @GetMapping("/{seriesId}")
    public List<RunDTO> getAllTickets(@PathVariable String seriesId) {
        return mapper.map(runService.getRunsBySeriesId(seriesId), AppConstants.RUN_LIST_TYPE);
    }

    @GetMapping("/{id}")
    public RunDTO getTicketById(@PathVariable Integer id) {
        return mapper.map(runService.getRunById(id), RunDTO.class);
    }

}
