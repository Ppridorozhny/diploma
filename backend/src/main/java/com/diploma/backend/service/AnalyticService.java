package com.diploma.backend.service;

import java.util.List;
import java.util.Map;

import com.diploma.backend.model.entities.DefectStatisticEntry;
import com.diploma.backend.model.entities.StatisticEntry;
import com.diploma.backend.model.entities.TicketTypeStatisticEntry;

public interface AnalyticService {

    List<StatisticEntry> getUserStatisticsByProjectId(Integer projectId);

    List<TicketTypeStatisticEntry> getTicketTypeStatisticsByProjectId(Integer projectId);

    Map<String, DefectStatisticEntry> getDefectsStatisticByProjectId(Integer projectId);

}
