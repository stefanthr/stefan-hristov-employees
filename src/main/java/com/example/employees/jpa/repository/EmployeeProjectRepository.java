package com.example.employees.jpa.repository;

import com.example.employees.jpa.model.EmployeeProject;
import com.example.employees.jpa.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, Long>,
        JpaSpecificationExecutor<EmployeeProject> {
    List<EmployeeProject> findByProjectAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Project project, LocalDate dateTo, LocalDate dateFrom);

}

