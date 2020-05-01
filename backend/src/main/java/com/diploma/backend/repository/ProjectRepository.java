package com.diploma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diploma.backend.model.entities.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    void deleteProjectById(Integer id);

}
