package com.diploma.backend.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diploma.backend.model.entities.DefectStatisticEntry;
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

    @Override
    @Transactional
    public Map<String, DefectStatisticEntry> getDefectsStatisticByProjectId(Integer projectId) {
        Map<String, DefectStatisticEntry> map = new HashMap<>();
        final List<StatisticEntry> opened =
                ticketRepository.getOpenedDefectsStatisticByProjectId(projectId);
        final List<StatisticEntry> closed =
                ticketRepository.getClosedDefectsStatisticByProjectId(projectId);

        DefectStatisticEntry defectStatisticEntry;

        for (StatisticEntry entry : opened) {
            defectStatisticEntry = new DefectStatisticEntry();
            defectStatisticEntry.setOpened(entry.getCount());
            map.put(entry.getName(), defectStatisticEntry);
        }

        for (StatisticEntry entry : closed) {
            if (map.containsKey(entry.getName())) {
                defectStatisticEntry = map.get(entry.getName());
                defectStatisticEntry.setClosed(entry.getCount());
            } else {
                defectStatisticEntry = new DefectStatisticEntry();
                defectStatisticEntry.setClosed(entry.getCount());
                map.put(entry.getName(), defectStatisticEntry);
            }

        }

        return map;
    }

}
