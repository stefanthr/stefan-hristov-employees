package com.example.employees.service;

import com.example.employees.jpa.model.Employee;
import com.example.employees.jpa.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

//TODO unit test EmployeeService
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

  @Mock
  private EmployeeRepository employeeRepository;

  @InjectMocks
  private EmployeeService employeeService;

  private Employee employee;

  @BeforeEach
  public void setup() {
    employee = Employee.builder()
        .id(1L)
        .firstName("Ramesh")
        .lastName("Fadatare")
        .build();
  }
}
