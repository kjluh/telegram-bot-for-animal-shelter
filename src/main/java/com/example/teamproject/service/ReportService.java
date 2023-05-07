package com.example.teamproject.service;

import com.example.teamproject.entities.AdoptiveParent;
import com.example.teamproject.entities.Pet;
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
     * Сохранить новый пустой отчет с заполнением полей с питомцем и усыновителем
     *
     * @param pet
     * @param adoptiveParent
     * @return
     */
    public Report saveNewReport(Pet pet, AdoptiveParent adoptiveParent) {
        Report newReport = new Report();
        newReport.setPet(pet);
        newReport.setAdoptiveParent(adoptiveParent);
        return loadReport(newReport);
    }

    /**
     * Сохранить в новый отчет фото
     *
     * @param photoId
     * @param adoptiveParent
     * @return
     */
    public Report savePhotoInNewReport(String photoId, AdoptiveParent adoptiveParent) {
        // Находим последний отчет у этого пользователя
        Report report = findLastReportByAdoptiveParentId(adoptiveParent.getId());
        report.setPhotoId(photoId);
        return loadReport(report);
    }

    /**
     * Сохранить в новый отчет диету
     *
     * @param diet
     * @param adoptiveParent
     * @return
     */
    public Report saveDietInNewReport(String diet, AdoptiveParent adoptiveParent) {
        Report report = findLastReportByAdoptiveParentId(adoptiveParent.getId());
        report.setDiet(diet);
        return loadReport(report);
    }

    /**
     * Сохранить в новый отчет состояние здоровья
     *
     * @param health
     * @param adoptiveParent
     * @return
     */
    public Report saveHealthInNewReport(String health, AdoptiveParent adoptiveParent) {
        Report report = findLastReportByAdoptiveParentId(adoptiveParent.getId());
        report.setHealth(health);
        return loadReport(report);
    }

    /**
     * Сохранить в новый отчет поведение
     *
     * @param behavior
     * @param adoptiveParent
     * @return
     */
    public Report saveBehaviorInNewReport(String behavior, AdoptiveParent adoptiveParent) {
        Report report = findLastReportByAdoptiveParentId(adoptiveParent.getId());
        report.setBehavior(behavior);
        report.setReportDate(LocalDate.now());
        return loadReport(report);
    }

    /**
     * Получаем список всех отчётов в приюте
     * @return list объектов класса REPORT из БД
     */
    public Collection<Report> getAllReports() {
        return reportRepository.findAll();
    }
    /**
     * Получаем отчёт по I
     * @param id для поиска отчёта по Id
     * @return list объектов класса REPORT из БД
     */
    public Report getReportById(Long id) {
        return reportRepository.findById(id).orElse(null);
    }
    /**
     * Получаем список всех отчётов в приюте
     * @param id для поиска по Id усыновителя
     * @return list объектов класса REPORT из БД
     */
    public Collection<Report> getAllReportsByAdoptiveParent(Long id) {
        return reportRepository.findReportsByAdoptiveParent_Id(id);
    }

    /**
     * Найти последний отчет в таблице по id усыновителя
     *
     * @param id
     * @return
     */
    public Report findLastReportByAdoptiveParentId(Long id) {
        return reportRepository.findLastReportByAdoptiveParentId(id);
    }

    /**
     * Обновляем отчёт в БД
     * @param report для изменения
     * @return возвращаем изменённый отчёт
     */
    public Report updateReport (Report report) {
        return reportRepository.save(report);
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
