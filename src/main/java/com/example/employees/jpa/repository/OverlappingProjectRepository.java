package com.example.employees.jpa.repository;

import com.example.employees.jpa.model.OverlappingProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OverlappingProjectRepository extends JpaRepository<OverlappingProject, Long> {
    Optional<OverlappingProject> findById(Long overlappingProjectId);
}
