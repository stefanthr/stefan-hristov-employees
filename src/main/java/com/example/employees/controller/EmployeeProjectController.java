package com.example.employees.controller;

import com.example.employees.service.EmployeeProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Controller
public class EmployeeProjectController {
    @Autowired
    private EmployeeProjectService employeeProjectService;

    @GetMapping("/")
    public String showUploadForm() {
        return "uploadForm";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        List<String> overlappingProjects;

        try {
            overlappingProjects = employeeProjectService.checkForOverlappingProjects(file);
        } catch (IOException e) {
            overlappingProjects = Collections.singletonList("Error processing file: " + e.getMessage());
        }

        model.addAttribute("overlappingProjects", overlappingProjects);

        return "results";
    }
}

