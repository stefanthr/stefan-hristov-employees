package com.example.employees.service;

import com.example.employees.jpa.model.Employee;
import com.example.employees.jpa.model.EmployeeProject;
import com.example.employees.jpa.model.EmployeeProjectId;
import com.example.employees.jpa.model.Project;
import com.example.employees.jpa.repository.EmployeeProjectRepository;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class EmployeeProjectService {

  private EmployeeService employeeService;
  private ProjectService projectService;
  private EmployeeProjectRepository employeeProjectRepository;

  public EmployeeProjectService(EmployeeService employeeService, ProjectService projectService,
      EmployeeProjectRepository employeeProjectRepository) {
    this.employeeService = employeeService;
    this.projectService = projectService;
    this.employeeProjectRepository = employeeProjectRepository;
  }

  protected EmployeeProject createEmployeeProject(Long employeeId, Long projectId,
      LocalDate startDate,
      LocalDate endDate) {
    Employee employee = employeeService.getEmployee(employeeId);
    Project project = projectService.getProject(projectId);
    EmployeeProject employeeProject = new EmployeeProject(
        new EmployeeProjectId(employee, project), employee, project, startDate, endDate);
    return employeeProject;
  }


}

