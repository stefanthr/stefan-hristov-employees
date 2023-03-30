package com.example.employees.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.employees.jpa.model.Project;
import com.example.employees.jpa.repository.ProjectRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

  @Mock
  private ProjectRepository projectRepository;
  private ProjectService projectService;
  private Project project;

  @BeforeEach
  public void setup() {
    projectService = new ProjectService(projectRepository);
    project = new Project();
  }

  @Test
  public void testGetProject_whenProjectExists() {
    Long projectId = 1L;
    project.setId(projectId);

    when(projectRepository.findById(project.getId()))
        .thenReturn(Optional.of(project));

    Project result = projectService.getProject(projectId);

    assertEquals(project, result);
    verify(projectRepository, times(1)).findById(projectId);
    verify(projectRepository, never()).save(any());
  }

  @Test
  public void testGetProject_whenProjectDoesNotExist() {
    Long projectId = 1L;
    when(projectRepository.findById(projectId)).thenReturn(Optional.empty());
    project.setId(projectId);
    when(projectRepository.save(any(Project.class))).thenReturn(project);

    Project result = projectService.getProject(projectId);

    verify(projectRepository, times(1)).findById(projectId);
    verify(projectRepository, times(1)).save(any(Project.class));
    assertEquals(projectId, result.getId());
  }
}
