package com.diploma.backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diploma.backend.model.entities.Run;
import com.diploma.backend.repository.RunRepository;
import com.diploma.backend.service.RunService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RunServiceImpl implements RunService {

    private final RunRepository runRepository;

    @Override
    public List<Run> getRunsBySeriesId(String seriesId) {
        return runRepository.getRunsBySeriesId(seriesId);
    }

    @Override
    public Run getRunById(Integer runId) {
        return runRepository.getOne(runId);
    }

}
