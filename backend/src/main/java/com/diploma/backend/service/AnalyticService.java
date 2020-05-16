package com.diploma.backend.service;

import java.util.List;

import com.diploma.backend.model.entities.StatisticEntry;
import com.diploma.backend.model.entities.TicketTypeStatisticEntry;

public interface AnalyticService {

    List<StatisticEntry> getUserStatisticsByProjectId(Integer projectId);

    List<TicketTypeStatisticEntry> getTicketTypeStatisticsByProjectId(Integer projectId);

}
