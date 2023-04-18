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

    /**
     * Отчеты от пользователя по питомцу
     */
    @OneToMany(mappedBy = "adoptiveParent")
    private Collection<Report> reports;

    @OneToMany(mappedBy = "id")
    private Collection<Pet> pets;


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

    public Collection<Report> getReports() {
        return reports;
    }

    public void setReports(Collection<Report> reports) {
        this.reports = reports;
    }

    public Collection<Pet> getPets() {
        return pets;
    }

    public void setPets(Collection<Pet> pets) {
        this.pets = pets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdoptiveParent that = (AdoptiveParent) o;
        return Objects.equals(id, that.id) && Objects.equals(chatId, that.chatId) && Objects.equals(name, that.name) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(address, that.address) && Objects.equals(message, that.message) && Objects.equals(reports, that.reports) && Objects.equals(pets, that.pets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, name, phoneNumber, address, message, reports, pets);
    }

    @Override
    public String toString() {
        return "AdoptiveParent{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", name='" + name + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", address='" + address + '\'' +
                ", message='" + message + '\'' +
                ", reports=" + reports +
                ", pets=" + pets +
                '}';
    }
}
