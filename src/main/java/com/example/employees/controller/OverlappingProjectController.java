package com.example.employees.controller;

import com.example.employees.exception.InvalidInputException;
import com.example.employees.jpa.model.OverlappingProject;
import com.example.employees.jpa.repository.OverlappingProjectRepository;
import com.example.employees.service.OverlappingProjectService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class OverlappingProjectController {

  @Autowired
  private OverlappingProjectRepository overlappingProjectRepository;

  @Autowired
  private OverlappingProjectService overlappingProjectService;

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @GetMapping("/")
  public String showUploadForm() {
    return "uploadForm";
  }

  @PostMapping("/upload")
  public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model)
      throws IllegalArgumentException {
    List<OverlappingProject> overlappingProjects = new ArrayList<>();
    try {
      overlappingProjects = overlappingProjectService.getOverlappingProjects(file);
    } catch (IOException ioException) {
      logger.error("Error processing file: " + ioException.getMessage());
    } catch (InvalidInputException invalidInputException) {
      logger.info("Invalid input: " + invalidInputException.getMessage());
    }
    model.addAttribute("overlappingProjects", overlappingProjects);
    return "results";
  }
}

