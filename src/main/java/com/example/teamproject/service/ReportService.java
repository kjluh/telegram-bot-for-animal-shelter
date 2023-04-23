package com.example.teamproject.service;

import com.example.teamproject.entities.Report;
import com.example.teamproject.repositories.ReportRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }
    /**
     * Метод Загружает отчёт в БД
     * @param newReport в формате JSON  клас Report
     * @return возвращает загруженный отчёт
     */
    public Report loadReport(Report newReport) {
        reportRepository.save(newReport);
        return newReport;
    }
    /**
     * Получаем список всех отчётов в приюте
     * @return list объектов класса REPORT из БД
     */
    public Collection<Report> getAllReports() {
        return reportRepository.findAll();
    }

    /**
     * Удаляем отчёт из БД
     * @param id ID для удаления
     * @return возвращаем удаленный отчёт
     */
    public Report deleteReport(Long id) {
        Report report = reportRepository.findById(id).orElseThrow();
        reportRepository.deleteById(id);
        return report;
    }
}
