package com.example.employees.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.employees.jpa.model.Employee;
import com.example.employees.jpa.repository.EmployeeRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    employee = new Employee();
  }

  @Test
  public void testGetEmployee_whenEmployeeExists() {
    Long employeeId = 1L;
    employee.setId(employeeId);

    when(employeeRepository.findById(employee.getId()))
        .thenReturn(Optional.of(employee));

    Employee result = employeeService.getEmployee(employeeId);

    assertEquals(employee, result);
    verify(employeeRepository, times(1)).findById(employeeId);
    verify(employeeRepository, never()).save(any());
  }

  @Test
  public void testGetEmployee_whenEmployeeDoesNotExist() {
    Long employeeId = 1L;
    when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());
    employee.setId(employeeId);
    when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

    Employee result = employeeService.getEmployee(employeeId);

    verify(employeeRepository, times(1)).findById(employeeId);
    verify(employeeRepository, times(1)).save(any(Employee.class));
    assertEquals(employeeId, result.getId());
  }

}
