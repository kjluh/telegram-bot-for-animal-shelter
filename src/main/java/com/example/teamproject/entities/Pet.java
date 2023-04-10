package com.example.teamproject.entities;

import jakarta.persistence.*;

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
}
