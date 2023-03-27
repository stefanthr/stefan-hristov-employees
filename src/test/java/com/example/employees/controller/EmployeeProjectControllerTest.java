package com.example.employees.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.employees.jpa.repository.OverlappingProjectRepository;
import com.example.employees.service.EmployeeProjectService;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;


//TODO unit test EmployeeProjectController
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

  }
}