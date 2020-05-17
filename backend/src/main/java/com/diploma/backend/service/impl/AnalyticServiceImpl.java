package com.diploma.backend.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diploma.backend.model.entities.DefectStatisticEntry;
import com.diploma.backend.model.entities.Run;
import com.diploma.backend.model.entities.RunInfo;
import com.diploma.backend.model.entities.StatisticEntry;
import com.diploma.backend.model.entities.Ticket;
import com.diploma.backend.model.entities.TicketRelation;
import com.diploma.backend.model.entities.TicketTypeStatisticEntry;
import com.diploma.backend.model.enums.RelationType;
import com.diploma.backend.repository.RunRepository;
import com.diploma.backend.repository.TicketRepository;
import com.diploma.backend.service.AnalyticService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AnalyticServiceImpl implements AnalyticService {

    private final TicketRepository ticketRepository;
    private final RunRepository runRepository;

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

    @Override
    public List<RunInfo> getRunStatistic(String seriesId) {
        Map<Integer, RunInfo> runInfoMap = new HashMap<>();
        List<Run> runs = runRepository.getRunsBySeriesId(seriesId);
        Ticket ticket;
        Ticket parent;
        RunInfo runInfo;
        RunInfo parentRunInfo;
        for (Run run : runs) {
            ticket = run.getTicket();
            parent = ticket.getRelations().stream()
                    .filter(ticketRelation -> ticketRelation.getRelationType() == RelationType.CHILD)
                    .findFirst().map(TicketRelation::getTarget).orElse(null);
            runInfo = new RunInfo();
            runInfo.setTicketId(ticket.getId());
            runInfo.setStartedWhen(run.getStartedWhen());
            runInfo.setTicketName(ticket.getName());
            runInfo.setCompletedWhen(Objects.requireNonNullElseGet(run.getCompletedWhen(),
                    () -> new Date(run.getStartedWhen().getTime() + run.getDuration())));

            if (parent != null) {
                runInfo.setParentId(parent.getId());

                if (!runInfoMap.containsKey(parent.getId())) {
                    parentRunInfo = new RunInfo();
                    parentRunInfo.setTicketName(parent.getName());
                    parentRunInfo.setTicketId(parent.getId());

                    runInfoMap.put(parent.getId(), parentRunInfo);
                }
            }

            runInfoMap.put(ticket.getId(), runInfo);
        };
        return runInfoMap.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

}
