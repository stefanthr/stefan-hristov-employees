package com.example.employees.jpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {

  @Id
  private Long id;

  @Column(name = "fist_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;
  @OneToMany(mappedBy = "employee")
  private List<EmployeeProject> employeeProjects;

  @OneToMany(mappedBy = "employee1")
  private List<OverlappingProject> overlappingProjects1;

  @OneToMany(mappedBy = "employee2")
  private List<OverlappingProject> overlappingProjects2;

  public Employee(Long id) {
    this.id = id;
  }
}
