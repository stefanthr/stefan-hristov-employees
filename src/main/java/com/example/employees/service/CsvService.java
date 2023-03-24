package com.example.employees.service;

import com.example.employees.exception.InvalidInputException;
import com.example.employees.jpa.model.EmployeeProject;
import com.example.employees.jpa.repository.EmployeeProjectRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CsvService {

  @Autowired
  private EmployeeProjectRepository employeeProjectRepository;

  @Autowired
  private EmployeeProjectService employeeProjectService;

  private Logger logger = LoggerFactory.getLogger(getClass());

  protected List<EmployeeProject> parseCsvFile(MultipartFile file)
      throws IOException, InvalidInputException {
    List<EmployeeProject> employeeProjects = new ArrayList<>();
    try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
      if (isHeaderValid(csvReader.readNext())) {
        String[] values = null;
        while ((values = csvReader.readNext()) != null) {
          parseInputTo(employeeProjects, values);
        }
      } else {
        throw new InvalidInputException("Invalid column names");
      }
    } catch (CsvValidationException csvValidationException) {
      logger.error("CSV Exception: " + csvValidationException.getMessage());
    }
    employeeProjectRepository.saveAll(employeeProjects);
    return employeeProjects;
  }

  private void parseInputTo(List<EmployeeProject> employeeProjects, String[] values)
      throws InvalidInputException {
    if (!isLineEmpty(values)) {
      parseLineToEmployeeProject(values, employeeProjects);
    } else {
      throw new InvalidInputException("Line cannot be empty");
    }
  }

  private boolean isLineEmpty(String[] contentLine) {
    return Arrays.stream(contentLine).allMatch(s -> s.equals(""));
  }

  private static boolean isHeaderValid(String[] header) {
    return Arrays.stream(header)
        .allMatch(
            s -> s.equals("EmpID") || s.equals("ProjectID") || s.equals("DateFrom") || s.equals(
                "DateTo"));
  }

  private void parseLineToEmployeeProject(String[] line, List<EmployeeProject> employeeProjects)
      throws InvalidInputException {
    if (!Arrays.asList(line).contains("")) {
      Long employeeId = Long.valueOf(line[0]);
      Long projectId = Long.valueOf(line[1]);
      LocalDate startDate = LocalDate.parse(line[2]);
      LocalDate endDate = line[3].equals("NULL") ? LocalDate.now() :
          LocalDate.parse(line[3]);
      EmployeeProject employeeProject = employeeProjectService.createEmployeeProject(employeeId,
          projectId, startDate, endDate);
      employeeProjects.add(employeeProject);
    } else {
      throw new InvalidInputException("Input value cannot be empty");
    }
  }

}
