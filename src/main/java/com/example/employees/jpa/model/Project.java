package com.example.employees.jpa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "project")
@Getter
@Setter
@NoArgsConstructor
public class Project {

    @Id
    private Long id;
    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @OneToMany(mappedBy = "project")
    private List<EmployeeProject> employeeProjects;

    @OneToOne(mappedBy = "project")
    private OverlappingProject overlappingProject;

    public Project(Long id) {
        this.id = id;
    }
}
