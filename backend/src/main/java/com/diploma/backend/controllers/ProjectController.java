package com.diploma.backend.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.diploma.backend.AppConstants;
import com.diploma.backend.model.dto.ProjectDTO;
import com.diploma.backend.model.entities.Project;
import com.diploma.backend.service.ProjectService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(tags = {"ProjectController"})
@Validated
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ProjectController {

    private final ProjectService projectService;
    private final ModelMapper mapper;

    @GetMapping
    public List<ProjectDTO> getAllProjects() {
        return mapper.map(projectService.getAllProjects(), AppConstants.PROJECT_LIST_TYPE);
    }

    @GetMapping("/{id}")
    public ProjectDTO getProjectById(@PathVariable Integer id) {
        return mapper.map(projectService.getProject(id), ProjectDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDTO createProject(@RequestBody @Validated ProjectDTO projectDTO) {
        Project project = mapper.map(projectDTO, Project.class);
        project = projectService.createProject(project);
        return mapper.map(project, ProjectDTO.class);
    }

    @PutMapping("/{id}")
    public ProjectDTO updateProject(@RequestBody @Validated ProjectDTO projectDTO, @PathVariable Integer id) {
        Project project = mapper.map(projectDTO, Project.class);
        project = projectService.updateProject(id, project);
        return mapper.map(project, ProjectDTO.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable Integer id) {
        projectService.deleteProject(id);
    }

}
