package com.example.teamproject.controllers;

import com.example.teamproject.entities.AdoptiveParent;
import com.example.teamproject.entities.Report;
import com.example.teamproject.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/Reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @Operation(
            summary = "Добавить отчёт в БД",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "415",
                            description = "Ошибка: Неверный формат данных",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @PostMapping()
    public Report saveReport(@Parameter(description = "Данные отчёта") @RequestBody Report report){
        return reportService.loadReport(report);
    }

    @Operation(
            summary = "Получить все отчёты",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "415",
                            description = "Ошибка: Что-то пошло не так!",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @GetMapping()
    public ResponseEntity<Collection<Report>> getAllReports() {
        return ResponseEntity.ok(reportService.getAllReports());
    }

    @Operation(
            summary = "Получить все отчёты определённого усыновителя по Id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "415",
                            description = "Ошибка: Что-то пошло не так!",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @GetMapping("/test/{id}")
    public ResponseEntity<Collection<Report>> getAllReportsByAdoptiveParent(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.getAllReportsByAdoptiveParent(id));
    }

    @Operation(
            summary = "Изменить отчёт",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "415",
                            description = "Ошибка: Отчёт не найден или данные не корректны",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @PutMapping()
    public ResponseEntity<Report> updateReport(@Parameter(description = "Данные отчёта для изменения") @RequestBody Report report) {
        return ResponseEntity.ok(reportService.updateReport(report));
    }

    @Operation(
            summary = "Удалить отчёт",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "415",
                            description = "Ошибка: Отчёт не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Report> deleteReport(@Parameter(description = "Id отчёта") @PathVariable Long id) {
        return ResponseEntity.ok(reportService.deleteReport(id));
    }
}
