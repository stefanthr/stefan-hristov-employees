package com.example.employees.controller;

import com.example.employees.jpa.model.OverlappingProject;
import com.example.employees.jpa.repository.OverlappingProjectRepository;
import com.example.employees.service.EmployeeProjectService;
import org.hibernate.sql.ast.tree.expression.Over;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class EmployeeProjectController {

    @Autowired
    private OverlappingProjectRepository overlappingProjectRepository;

    @Autowired
    private EmployeeProjectService employeeProjectService;

    @GetMapping("/")
    public String showUploadForm() {
        return "uploadForm";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        Logger logger = LoggerFactory.getLogger(EmployeeProjectController.class);
        List<OverlappingProject> overlappingProjects = new ArrayList<>();
        try {
            overlappingProjects = employeeProjectService.checkForOverlappingProjects(file);
        } catch (IOException e) {
            logger.info("Error processing file: " + e.getMessage());
        }
        model.addAttribute("employee1Id", overlappingProjects.get(0).getEmployee1().getId());
        model.addAttribute("employee2Id", overlappingProjects.get(0).getEmployee2().getId());
        model.addAttribute("projectId", overlappingProjects.get(0).getProject().getId());

        return "results";
    }
}

