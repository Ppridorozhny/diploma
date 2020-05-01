package com.diploma.backend.service;

import java.util.List;

import com.diploma.backend.model.entities.Project;

public interface ProjectService {

    Project createProject(Project project);

    Project updateProject(Integer id, Project project);

    Project getProject(Integer id);

    void deleteProject(Integer id);

    List<Project> getAllProjects();

}
