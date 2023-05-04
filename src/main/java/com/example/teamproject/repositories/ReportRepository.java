package com.example.teamproject.repositories;

import com.example.teamproject.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;


public interface ReportRepository extends JpaRepository<Report, Long> {
    Collection<Report> findReportsByAdoptiveParent_Id(Long id);

    // Найти последний отчет в таблице report по усыновителю.
    @Query(value = "SELECT * FROM report WHERE parent_id=?1 ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Report findLastReportByAdoptiveParentId(Long id);
}
