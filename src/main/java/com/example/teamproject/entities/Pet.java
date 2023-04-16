package com.example.teamproject.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
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
    private Long parentId;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return age == pet.age && Objects.equals(id, pet.id) && Objects.equals(parentId, pet.parentId) && Objects.equals(type, pet.type) && Objects.equals(name, pet.name) && Objects.equals(description, pet.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parentId, type, name, age, description);
    }

    @Override
    public String toString() {
        return "Pet{" +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", description='" + description + '\'' +
                '}';
    }
}
