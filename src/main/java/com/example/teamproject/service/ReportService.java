package com.example.teamproject.service;

import com.example.teamproject.entities.Report;
import com.example.teamproject.repositories.ReportRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    @Autowired
    private TelegramBot telegramBot;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Report loadReport(byte[] photo, String diet, String health, String behavior, LocalDate reportDate) {
        Report newReport = new Report();
        newReport.setPhoto(photo);
        newReport.setDiet(diet);
        newReport.setHealth(health);
        newReport.setBehavior(behavior);
        newReport.setReportDate(reportDate);
        return newReport;
    }

    public void savePhoto(Update update){
        PhotoSize photoSize = update.message().photo()[update.message().photo().length - 1]; // из массива фото берем последнее в качестве
        GetFileResponse getFileResponse = telegramBot.execute(
                new GetFile(photoSize.fileId()));  // получение файла в чате
        if (getFileResponse.isOk()) { // если ответ на получение файла положительный
            try {
                String extension = StringUtils.getFilenameExtension(getFileResponse.file().filePath());  // получаем расширение файла
                byte[] image = telegramBot.getFileContent(getFileResponse.file()); // получаем картинку в виде массива байт
                Files.write(Paths.get(UUID.randomUUID() + "." + extension), image); // сохраняем картинку на комп
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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
