package com.example.teamproject.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private AdoptiveParent adoptiveParent;

    private String type;

    private String name;

    private int age;

    private String description;

    public Long getId() {
        return id;
    }

    public AdoptiveParent getAdoptiveParent() {
        return adoptiveParent;
    }

    public void setAdoptiveParent(AdoptiveParent adoptiveParent) {
        this.adoptiveParent = adoptiveParent;
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
        return age == pet.age && Objects.equals(id, pet.id) && Objects.equals(adoptiveParent, pet.adoptiveParent) && Objects.equals(type, pet.type) && Objects.equals(name, pet.name) && Objects.equals(description, pet.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, adoptiveParent, type, name, age, description);
    }

    @Override
    public String toString() {
        return "Pet{" +
                "adoptiveParent=" + adoptiveParent +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", description='" + description + '\'' +
                '}';
    }
}
