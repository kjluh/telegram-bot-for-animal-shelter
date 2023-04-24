package com.example.teamproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;


@Entity
@Getter
@Setter
public class Pet {
    /**
     * Уникальный ID для хранения класса в БД и использования экземпляра в программе
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Владелец питомца
     */
    @ManyToOne
    @JsonIgnore
    private AdoptiveParent adoptiveParent;

    /**
     * Тип питомца
     */
    private String type;

    /**
     * Имя питомца
     */
    private String name;

    /**
     * Возраст питомца
     */
    private int age;

    /**
     * Описание питомца
     */
    private String description;
    /**
     * Дата окончания испытательного срока
     */
    private LocalDate trialPeriod;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return age == pet.age && Objects.equals(id, pet.id) && Objects.equals(adoptiveParent, pet.adoptiveParent) && Objects.equals(type, pet.type) && Objects.equals(name, pet.name) && Objects.equals(description, pet.description) && Objects.equals(trialPeriod, pet.trialPeriod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, adoptiveParent, type, name, age, description, trialPeriod);
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", adoptiveParent=" + adoptiveParent +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", description='" + description + '\'' +
                ", trialPeriod=" + trialPeriod +
                '}';
    }
}
