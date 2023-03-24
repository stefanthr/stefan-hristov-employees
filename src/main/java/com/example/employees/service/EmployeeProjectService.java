package com.example.employees.service;

import com.example.employees.jpa.model.Employee;
import com.example.employees.jpa.model.EmployeeProject;
import com.example.employees.jpa.model.EmployeeProjectId;
import com.example.employees.jpa.model.Project;
import com.example.employees.jpa.repository.EmployeeProjectRepository;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeProjectService {

  @Autowired
  private EmployeeService employeeService;

  @Autowired
  private ProjectService projectService;

  @Autowired
  private EmployeeProjectRepository employeeProjectRepository;


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

