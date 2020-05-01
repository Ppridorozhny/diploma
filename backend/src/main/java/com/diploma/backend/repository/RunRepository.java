package com.diploma.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diploma.backend.model.entities.Run;

public interface RunRepository extends JpaRepository<Run, Integer> {

    List<Run> getRunsBySeriesId(String seriesId);

}
