package com.diploma.backend.service;

import java.util.List;

import com.diploma.backend.model.entities.Run;

public interface RunService {

    List<Run> getRunsBySeriesId(String seriesId);

    Run getRunById(Integer runId);

}
