package com.diploma.backend.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diploma.backend.error.exceptions.ResourceNotFoundException;
import com.diploma.backend.model.entities.Project;
import com.diploma.backend.repository.ProjectRepository;
import com.diploma.backend.service.ProjectService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Integer id, Project project) {
        Project projectFromCache = projectRepository.getOne(id);
        BeanUtils.copyProperties(project, projectFromCache, "id");
        return projectRepository.save(projectFromCache);
    }

    @Override
    public Project getProject(Integer id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteProject(Integer id) {
        projectRepository.deleteProjectById(id);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

}
