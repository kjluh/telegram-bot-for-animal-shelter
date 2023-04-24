package com.example.teamproject.controller;

import com.example.teamproject.entities.Report;
import com.example.teamproject.repositories.ReportRepository;
import com.example.teamproject.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/teamProject8/Report")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping()
    public Collection<Report> getAllReports() {
        return reportService.getAllReports();
    }
}
