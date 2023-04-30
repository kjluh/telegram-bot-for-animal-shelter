package com.example.teamproject.repositories;

import com.example.teamproject.entities.AdoptiveParent;
import com.example.teamproject.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


public interface ReportRepository extends JpaRepository<Report, Long> {
    Collection<Report> findReportsByAdoptiveParent_Id(Long id);
}
