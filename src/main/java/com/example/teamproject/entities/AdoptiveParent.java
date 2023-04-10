package com.example.teamproject.entities;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

/**
 * Сущность для создания сообщения от пользователя и последующим хранением в бд
 * В классе несколько параметров: id, chatId, name, phoneNumber, message и стандартные геттеры, сеттеры,
 * equals и hashCode.
 */
@Entity
public class AdoptiveParent {
    /**
     * Уникальный ID для хранения класса в БД и использования экземпляра в программе
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ID чата от куда пришел пользователь
     */
    private Long chatId;

    /**
     * Имя пользователя
     */
    private String name;

    /**
     * Номер телефона пользователя
     */
    private Long phoneNumber;

    /**
     * Адрес пользователя
     */
    private String address;

    /**
     * Сообщение от пользователя
     */
    private String message;

    @OneToMany(mappedBy = "adoptiveParent")
    private Collection<Report> reports;


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

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "AdoptiveParent{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", name='" + name + '\'' +
                ", phoneNumber=" + phoneNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdoptiveParent that = (AdoptiveParent) o;
        return phoneNumber == that.phoneNumber && id.equals(that.id) && chatId.equals(that.chatId) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, name, phoneNumber);
    }
}