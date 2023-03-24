package com.example.employees.service;

import com.example.employees.jpa.model.Project;
import com.example.employees.jpa.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

  @Autowired
  private ProjectRepository projectRepository;

  protected Project getProject(Long projectId) {
    return projectRepository.findById(projectId)
        .orElseGet(() -> {
          Project newProject = new Project();
          newProject.setId(projectId);
          return projectRepository.save(newProject);
        });
  }
}
