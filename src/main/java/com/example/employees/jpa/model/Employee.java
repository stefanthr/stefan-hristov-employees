package com.example.employees.jpa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
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
}
