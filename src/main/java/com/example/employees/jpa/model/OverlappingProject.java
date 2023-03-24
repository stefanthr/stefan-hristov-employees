package com.example.employees.jpa.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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
  private long daysWorkedTogether;

  public OverlappingProject(Employee employee1, Employee employee2,
      Project project, long daysWorkedTogether) {
    this.employee1 = employee1;
    this.employee2 = employee2;
    this.project = project;
    this.daysWorkedTogether = daysWorkedTogether;
  }

  @Override
  public boolean equals(Object object) {
    if (object == this) {
      return true;
    } else if (object == null || !(object instanceof OverlappingProject)) {
      return false;
    } else {
      OverlappingProject otherEmployeeProject = (OverlappingProject) object;
      return isEmployee1IdEqualTo(otherEmployeeProject) &&
          isEmployee2IdEqualTo(otherEmployeeProject)
          && isProjectIdEqualTo(otherEmployeeProject);
    }
  }

  private boolean isProjectIdEqualTo(OverlappingProject other) {
    return Objects.equals(project.getId(), other.project.getId());
  }

  private boolean isEmployee2IdEqualTo(OverlappingProject other) {
    return Objects.equals(employee2.getId(), other.employee2.getId()) || Objects.equals(
        employee2.getId(), other.employee1.getId());
  }

  private boolean isEmployee1IdEqualTo(OverlappingProject other) {
    return Objects.equals(employee1.getId(), other.employee1.getId()) || Objects.equals(
        employee1.getId(), other.employee2.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(employee1.getId() ^ employee2.getId() ^ project.getId());
  }
}
