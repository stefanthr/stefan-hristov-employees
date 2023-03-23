package com.example.employees.service;

import com.example.employees.jpa.model.Employee;
import com.example.employees.jpa.model.EmployeeProject;
import com.example.employees.jpa.model.Project;
import com.example.employees.jpa.repository.EmployeeProjectRepository;
import com.example.employees.jpa.repository.EmployeeRepository;
import com.example.employees.jpa.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeProjectService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeProjectRepository employeeProjectRepository;

    public List<String> checkForOverlappingProjects(MultipartFile file) throws IOException {
        List<EmployeeProject> employeeProjects = parseCsvFile(file);

        List<String> overlappingProjects = new ArrayList<>();

        for (EmployeeProject employeeProject : employeeProjects) {
            List<EmployeeProject> otherEmployeeProjects = employeeProjectRepository.findByProjectAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                    employeeProject.getProject(), employeeProject.getStartDate(), employeeProject.getEndDate());

            for (EmployeeProject otherEmployeeProject : otherEmployeeProjects) {
                if (employeeProject.getEmployee().getId().equals(otherEmployeeProject.getEmployee().getId())) {
                    continue;
                }

                overlappingProjects.add("Employees " + employeeProject.getEmployee().getId() +
                        " and " + otherEmployeeProject.getEmployee().getId() + " worked on project " +
                        employeeProject.getProject().getId() + " at the same time");
            }
        }

        return overlappingProjects;
    }

    private List<EmployeeProject> parseCsvFile(MultipartFile file) throws IOException {
        List<EmployeeProject> employeeProjects = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            br.readLine(); // this will read the first line
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Long employeeId = Long.valueOf(values[0]);
                Long projectId = Long.valueOf(values[1]);
                LocalDate startDate = LocalDate.parse(values[2]);
                LocalDate endDate = values[3] == null ? null : LocalDate.now();

                Employee employee = employeeRepository.findById(employeeId)
                        .orElseGet(() -> {
                            Employee newEmployee = new Employee();
                            newEmployee.setId(employeeId);
                            return employeeRepository.save(newEmployee);
                        });

                Project project = projectRepository.findById(projectId)
                        .orElseGet(() -> {
                            Project newProject = new Project();
                            newProject.setId(projectId);
                            return projectRepository.save(newProject);
                        });

                EmployeeProject employeeProject = new EmployeeProject();
                employeeProject.setEmployee(employee);
                employeeProject.setProject(project);
                employeeProject.setStartDate(startDate);
                employeeProject.setEndDate(endDate);

                employeeProjects.add(employeeProject);
            }
        }
        employeeProjectRepository.saveAll(employeeProjects);
        return employeeProjects;
    }
}

