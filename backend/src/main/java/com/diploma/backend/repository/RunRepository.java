package com.diploma.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.diploma.backend.model.entities.Run;

public interface RunRepository extends JpaRepository<Run, Integer> {

    List<Run> getRunsBySeriesId(String seriesId);

    @Query("select distinct run.seriesId from Run run join run.ticket ticket where ticket.projectId = ?1 order by 1")
    List<String> getSeriesByProjectId(Integer projectId);

}
