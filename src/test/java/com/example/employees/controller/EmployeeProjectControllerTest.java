package com.example.employees.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.example.employees.jpa.model.Employee;
import com.example.employees.jpa.model.OverlappingProject;
import com.example.employees.jpa.model.Project;
import com.example.employees.jpa.repository.OverlappingProjectRepository;
import com.example.employees.service.EmployeeProjectService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;


@SpringBootTest
public class EmployeeProjectControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OverlappingProjectRepository overlappingProjectRepository;

  @MockBean
  private EmployeeProjectService employeeProjectService;

  @Mock
  private OverlappingProjectController employeeProjectController;

  @Mock
  private Model model;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testShowUploadForm() {
    String result = employeeProjectController.showUploadForm();
    assertEquals("uploadForm", result);
  }

  @Test
  public void testHandleFileUpload() throws IOException {
    MockMultipartFile mockFile = new MockMultipartFile("file", "test.csv", "text/csv",
        "test".getBytes());
    List<OverlappingProject> overlappingProjects = new ArrayList<>();
    overlappingProjects.add(
        new OverlappingProject(new Employee(223L), new Employee(334L), new Project(335L), 5));

    when(employeeProjectService.checkForOverlappingProjects(mockFile)).thenReturn(
        overlappingProjects);
    when(model.addAttribute("employee1Id",
        overlappingProjects.get(0).getEmployee1().getId())).thenReturn(model);
    when(model.addAttribute("employee2Id",
        overlappingProjects.get(0).getEmployee2().getId())).thenReturn(model);
    when(model.addAttribute("projectId",
        overlappingProjects.get(0).getProject().getId())).thenReturn(model);

    String result = employeeProjectController.handleFileUpload(mockFile, model);
    assertEquals("results", result);
  }
}