package com.diploma.backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diploma.backend.model.entities.StatisticEntry;
import com.diploma.backend.model.entities.TicketTypeStatisticEntry;
import com.diploma.backend.repository.TicketRepository;
import com.diploma.backend.service.AnalyticService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AnalyticServiceImpl implements AnalyticService {

    private final TicketRepository ticketRepository;

    @Override
    public List<StatisticEntry> getUserStatisticsByProjectId(Integer projectId) {
        return ticketRepository.getUserStatisticByProjectId(projectId);
    }

    @Override
    public List<TicketTypeStatisticEntry> getTicketTypeStatisticsByProjectId(Integer projectId) {
        return ticketRepository.getTicketTypeStatisticByProjectId(projectId);
    }

}
