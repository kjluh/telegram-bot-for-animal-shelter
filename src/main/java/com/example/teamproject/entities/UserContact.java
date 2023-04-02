package com.example.teamproject.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class UserContact {
    @Id
    @GeneratedValue
    private Long id;
    private Long chatId;
    private String name;
    private int phoneNumber;

    public UserContact(Long chatId, String name, int phoneNumber) {
        this.chatId = chatId;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public UserContact() {

    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
