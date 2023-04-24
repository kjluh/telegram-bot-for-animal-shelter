package com.example.teamproject.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Сущность для создания отчётов от усыновителей
 * В классе несколько параметров: id, parentId, petId, photo, diet, health, behavior и стандартные геттеры, сеттеры,
 * equals и hashCode.
 */
@Getter
@Setter
@Entity
public class Report {
    /**
     * Уникальный ID для хранения класса в БД и использования экземпляра в программе
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Владелец животного
     */
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private AdoptiveParent adoptiveParent;

    /**
     * Животное, отчет которого присылается
     */
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    /**
     * Фото животного в отчёте
     */
    private String photoId;

    /**
     * Диета животного
     */
    private String diet;

    /**
     * Общее самочувствие животного
     */
    private String health;

    /**
     * Поведение животного
     */
    private String behavior;

    /**
     * Дата отчёта
     */
    private LocalDate reportDate;

    public Report() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return Objects.equals(id, report.id) && Objects.equals(adoptiveParent, report.adoptiveParent) && Objects.equals(pet, report.pet) && Objects.equals(photoId, report.photoId) && Objects.equals(diet, report.diet) && Objects.equals(health, report.health) && Objects.equals(behavior, report.behavior) && Objects.equals(reportDate, report.reportDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, adoptiveParent, pet, photoId, diet, health, behavior, reportDate);
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", photo=" + photoId +
                ", diet='" + diet + '\'' +
                ", health='" + health + '\'' +
                ", behavior='" + behavior + '\'' +
                ", reportDate=" + reportDate +
                '}';
    }
}
