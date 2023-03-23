package com.example.employees.jpa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "overlapping_project")
public class OverlappingProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee1", nullable = false)
    private Employee employee1;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee2", nullable = false)
    private Employee employee2;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "days_worked_together")
    private int daysWorkedTogether;

    public OverlappingProject(Employee employee1, Employee employee2, Project project, int daysWorkedTogether) {
        this.employee1 = employee1;
        this.employee2 = employee2;
        this.project = project;
        this.daysWorkedTogether = daysWorkedTogether;
    }
}
