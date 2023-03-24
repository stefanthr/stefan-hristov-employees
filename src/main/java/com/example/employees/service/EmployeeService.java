package com.example.employees.service;

import com.example.employees.jpa.model.Employee;
import com.example.employees.jpa.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

  @Autowired
  private EmployeeRepository employeeRepository;

  protected Employee getEmployee(Long employeeId) {
    return employeeRepository.findById(employeeId)
        .orElseGet(() -> {
          Employee newEmployee = new Employee();
          newEmployee.setId(employeeId);
          return employeeRepository.save(newEmployee);
        });
  }

}
