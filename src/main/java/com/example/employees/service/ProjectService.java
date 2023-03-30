package com.example.employees.service;

import com.example.employees.jpa.model.Project;
import com.example.employees.jpa.repository.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

  private ProjectRepository projectRepository;

  public ProjectService(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  protected Project getProject(Long projectId) {
    return projectRepository.findById(projectId)
        .orElseGet(() -> {
          Project newProject = new Project();
          newProject.setId(projectId);
          return projectRepository.save(newProject);
        });
  }
}
