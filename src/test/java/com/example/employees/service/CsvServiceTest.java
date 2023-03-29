package com.example.employees.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.employees.jpa.model.EmployeeProject;
import com.example.employees.jpa.repository.EmployeeProjectRepository;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
public class CsvServiceTest {

  @Mock
  private EmployeeProjectRepository employeeProjectRepository;

  @Mock
  private EmployeeProjectService employeeProjectService;

  @InjectMocks
  private CsvService csvService;

  @Test
  void testParseCsvFile() throws IOException {
    String csvData = "EmpID,ProjectID,DateFrom,DateTo\n" +
        "1,1,2021-01-01,NULL\n" +
        "2,2,2021-02-01,2021-03-01\n" +
        "3,3,2021-03-01,2021-04-01\n";
    InputStream inputStream = new ByteArrayInputStream(csvData.getBytes(StandardCharsets.UTF_8));
    MockMultipartFile file = new MockMultipartFile("data.csv", inputStream);

    when(employeeProjectService.createEmployeeProject(anyLong(), anyLong(), any(),
        any())).thenReturn(new EmployeeProject());

    List<EmployeeProject> result = csvService.parseCsvFile(file);

    verify(employeeProjectRepository, times(1)).saveAll(anyList());
    assertEquals(3, result.size());
  }
}

