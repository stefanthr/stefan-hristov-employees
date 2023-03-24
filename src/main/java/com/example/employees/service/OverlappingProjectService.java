package com.example.employees.service;

import com.example.employees.jpa.model.EmployeeProject;
import com.example.employees.jpa.model.OverlappingProject;
import com.example.employees.jpa.repository.EmployeeProjectRepository;
import com.example.employees.jpa.repository.OverlappingProjectRepository;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class OverlappingProjectService {

  @Autowired
  private OverlappingProjectRepository overlappingProjectRepository;

  @Autowired
  private EmployeeProjectRepository employeeProjectRepository;

  @Autowired
  private EmployeeProjectService employeeProjectService;

  public List<OverlappingProject> getOverlappingProjects(MultipartFile file)
      throws IOException {
    List<OverlappingProject> overlappingProjects = new ArrayList<>();
    List<EmployeeProject> employeeProjects = employeeProjectService.parseCsvFile(file);
    for (EmployeeProject employeeProject : employeeProjects) {
      findOverlappingProjects(employeeProject, overlappingProjects);
    }
    List<OverlappingProject> uniqueOverlappingProjects = filterOverlappingProjects(
        overlappingProjects);
    overlappingProjectRepository.saveAll(uniqueOverlappingProjects);
    return uniqueOverlappingProjects;
  }

  private static List<OverlappingProject> filterOverlappingProjects(
      List<OverlappingProject> overlappingProjects) {
    Set<OverlappingProject> overlappingProjectsSet = new HashSet<>(overlappingProjects);
    List<OverlappingProject> uniqueOverlappingProjects = new ArrayList<>(overlappingProjectsSet);
    return uniqueOverlappingProjects;
  }

  private void findOverlappingProjects(EmployeeProject employeeProject,
      List<OverlappingProject> overlappingProjects) {
    List<EmployeeProject> otherEmployeeProjects =
        employeeProjectRepository.findByProject(employeeProject.getProject());
    for (EmployeeProject otherEmployeeProject : otherEmployeeProjects) {
      if (areEmployeeIdsDifferent(employeeProject, otherEmployeeProject)
          && isProjectOverlapping(employeeProject, otherEmployeeProject)) {
        long overlappingDays = getOverlappingDays(employeeProject, otherEmployeeProject);
        OverlappingProject overlappingProject = new OverlappingProject(
            employeeProject.getEmployee(), otherEmployeeProject.getEmployee(),
            employeeProject.getProject(), overlappingDays);
        overlappingProjects.add(overlappingProject);
      }
    }
  }

  private static long getOverlappingDays(EmployeeProject employeeProject,
      EmployeeProject otherEmployeeProject) {
    LocalDate overlapStart = getOverlapStart(employeeProject, otherEmployeeProject);
    LocalDate overlapEnd = getOverlapEnd(employeeProject, otherEmployeeProject);
    long overlappingDays = ChronoUnit.DAYS.between(overlapStart, overlapEnd);
    return overlappingDays;
  }

  private static LocalDate getOverlapEnd(EmployeeProject employeeProject,
      EmployeeProject otherEmployeeProject) {
    return employeeProject.getEndDate().isBefore(otherEmployeeProject.getEndDate())
        ? employeeProject.getEndDate() : otherEmployeeProject.getEndDate();
  }

  private static LocalDate getOverlapStart(EmployeeProject employeeProject,
      EmployeeProject otherEmployeeProject) {
    return employeeProject.getStartDate().isBefore(otherEmployeeProject.getStartDate())
        ? otherEmployeeProject.getStartDate() : employeeProject.getStartDate();
  }

  private boolean areEmployeeIdsDifferent(EmployeeProject employeeProject,
      EmployeeProject otherEmployeeProject) {
    return !employeeProject.getEmployee().getId()
        .equals(otherEmployeeProject.getEmployee().getId());
  }

  private boolean isProjectOverlapping(EmployeeProject employeeProject,
      EmployeeProject otherEmployeeProject) {
    return employeeProject.getStartDate()
        .isBefore(otherEmployeeProject.getEndDate())
        && otherEmployeeProject.getStartDate().isBefore(employeeProject.getEndDate());
  }
}
