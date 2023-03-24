package com.example.employees.service;

import com.example.employees.jpa.model.Employee;
import com.example.employees.jpa.model.EmployeeProject;
import com.example.employees.jpa.model.EmployeeProjectId;
import com.example.employees.jpa.model.Project;
import com.example.employees.jpa.repository.EmployeeProjectRepository;
import com.example.employees.jpa.repository.EmployeeRepository;
import com.example.employees.jpa.repository.ProjectRepository;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeProjectService {

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private EmployeeProjectRepository employeeProjectRepository;


  protected EmployeeProject createEmployeeProject(Long employeeId, Long projectId,
      LocalDate startDate,
      LocalDate endDate) {
    Employee employee = getEmployee(employeeId);
    Project project = getProject(projectId);
    EmployeeProject employeeProject = new EmployeeProject(
        new EmployeeProjectId(employee, project), employee, project, startDate, endDate);
    return employeeProject;
  }

  private Project getProject(Long projectId) {
    return projectRepository.findById(projectId)
        .orElseGet(() -> {
          Project newProject = new Project();
          newProject.setId(projectId);
          return projectRepository.save(newProject);
        });
  }

  private Employee getEmployee(Long employeeId) {
    return employeeRepository.findById(employeeId)
        .orElseGet(() -> {
          Employee newEmployee = new Employee();
          newEmployee.setId(employeeId);
          return employeeRepository.save(newEmployee);
        });
  }
}

