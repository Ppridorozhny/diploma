package com.diploma.backend.service;

import java.util.List;

import com.diploma.backend.model.entities.UserStatistic;

public interface AnalyticService {

    List<UserStatistic> getUserStatisticsByProjectId(Integer projectId);

}
