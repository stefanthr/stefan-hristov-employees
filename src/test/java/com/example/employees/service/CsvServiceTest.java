package com.example.employees.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.employees.exception.InvalidInputException;
import com.example.employees.jpa.model.EmployeeProject;
import com.example.employees.jpa.repository.EmployeeProjectRepository;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
public class CsvServiceTest {

  @Mock
  private EmployeeProjectRepository employeeProjectRepository;
  @Mock
  private EmployeeProjectService employeeProjectService;
  private CsvService csvService;

  @BeforeEach
  void setUp() {
    csvService = new CsvService(employeeProjectRepository, employeeProjectService);
  }


  @Test
  void testParseCsvFile_whenThreeEmployeeProjects() throws IOException {
    String csvData = "EmpID,ProjectID,DateFrom,DateTo\n" +
        "1,1,2021-01-01,NULL\n" +
        "2,2,2021-02-01,2021-03-01\n" +
        "3,3,2021-03-01,2021-04-01\n";
    InputStream inputStream = new ByteArrayInputStream(csvData.getBytes(StandardCharsets.UTF_8));
    MockMultipartFile file = new MockMultipartFile("data.csv", inputStream);

    List<EmployeeProject> result = csvService.parseCsvFile(file);

    verify(employeeProjectRepository, times(1)).saveAll(anyList());
    assertThat(result.size()).isEqualTo(3);
  }

  @Test
  void testParseInputTo_whenDateToIsNow() throws InvalidInputException {
    List<EmployeeProject> employeeProjects = new ArrayList<>();
    String[] values = {"1", "1", "2021-01-01", "null"};

    csvService.parseInputTo(employeeProjects, values);

    verify(employeeProjectService, times(1)).createEmployeeProject(1L, 1L,
        LocalDate.parse("2021-01-01"), LocalDate.now());
    assertThat(employeeProjects.size()).isEqualTo(1);
  }

  @Test
  void testIsLineEmpty() {
    assertThat(csvService.isLineEmpty(new String[]{"", "", ""})).isTrue();
    assertThat(csvService.isLineEmpty(new String[]{"", "a", ""})).isFalse();
  }

  @Test
  void testIsHeaderValid() {
    assertThat(csvService.isHeaderValid(
        new String[]{"EmpID", "ProjectID", "DateFrom", "DateTo"})).isTrue();
    assertThat(csvService.isHeaderValid(
        new String[]{"EmpID", "ProjectID", "DateFrom", "InvalidHeader"})).isFalse();
  }

  @Test
  void testParseLineToEmployeeProject() throws InvalidInputException {
    List<EmployeeProject> employeeProjects = new ArrayList<>();
    String[] values = {"1", "1", "2021-01-01", "2021-01-04"};

    csvService.parseLineToEmployeeProject(values, employeeProjects);

    verify(employeeProjectService, times(1)).createEmployeeProject(1L, 1L,
        LocalDate.parse("2021-01-01"), LocalDate.parse("2021-01-04"));
    assertThat(employeeProjects.size()).isEqualTo(1);
  }

  @Test
  void testParseLineToEmployeeProject_whenInputValueIsEmpty() throws InvalidInputException {
    List<EmployeeProject> employeeProjects = new ArrayList<>();
    String[] values = {"1", "1", "", "2021-01-04"};

    assertThatThrownBy(() ->
        csvService.parseLineToEmployeeProject(values, employeeProjects)).isInstanceOf(
        InvalidInputException.class);
  }
}
