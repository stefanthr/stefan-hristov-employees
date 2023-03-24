package com.example.employees.service;

import com.example.employees.jpa.model.Employee;
import com.example.employees.jpa.model.EmployeeProject;
import com.example.employees.jpa.model.EmployeeProjectId;
import com.example.employees.jpa.model.Project;
import com.example.employees.jpa.repository.EmployeeProjectRepository;
import com.example.employees.jpa.repository.EmployeeRepository;
import com.example.employees.jpa.repository.ProjectRepository;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EmployeeProjectService {

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private EmployeeProjectRepository employeeProjectRepository;

  protected List<EmployeeProject> parseCsvFile(MultipartFile file) throws
      IOException {
    List<EmployeeProject> employeeProjects = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
      String headerLine = br.readLine();
      String contentLine = "";
      while ((contentLine = br.readLine()) != null) {
        if (!contentLine.matches("^,*$")) {
          parseLineToEmpoyeeProject(contentLine, employeeProjects);
        }
      }
    }
    employeeProjectRepository.saveAll(employeeProjects);
    return employeeProjects;
  }

  private void parseLineToEmpoyeeProject(String line, List<EmployeeProject> employeeProjects) {
    String[] values = line.split(",");
    Long employeeId = Long.valueOf(values[0]);
    Long projectId = Long.valueOf(values[1]);
    LocalDate startDate = LocalDate.parse(values[2]);
    LocalDate endDate = values[3].equals("NULL") ? LocalDate.now() :
        LocalDate.parse(values[3]);
    EmployeeProject employeeProject = createEmployeeProject(employeeId,
        projectId, startDate, endDate);
    employeeProjects.add(employeeProject);
  }

  private EmployeeProject createEmployeeProject(Long employeeId, Long projectId,
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

