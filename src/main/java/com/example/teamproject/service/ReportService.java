package com.example.teamproject.service;

import com.example.teamproject.entities.Report;
import com.example.teamproject.repositories.ReportRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Report loadReport(byte[] photo, String diet, String health, String behavior, LocalDate reportDate) {
        Report newReport = new Report();
//        newReport.setPhoto(photo);
        newReport.setDiet(diet);
        newReport.setHealth(health);
        newReport.setBehavior(behavior);
        newReport.setReportDate(reportDate);
        return newReport;
    }

    public Collection<Report> getAllReports() {
        return reportRepository.findAll();
    }


    public Report deleteReport(Long id) {
        Report report = reportRepository.findById(id).orElseThrow();
        reportRepository.deleteById(id);
        return report;
    }
}
