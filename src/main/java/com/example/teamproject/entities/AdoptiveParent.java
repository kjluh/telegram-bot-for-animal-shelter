package com.example.teamproject.entities;

import com.example.teamproject.utils.Util;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;

/**
 * Сущность для создания сообщения от пользователя и последующим хранением в бд
 * В классе несколько параметров: id, chatId, name, phoneNumber, message и стандартные геттеры, сеттеры,
 * equals и hashCode.
 */
@Getter
@Setter
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
     * Статус заполнения отчета о питомце
     */
    private Util.ReportStatus reportStatus;

    /**
     * Отчеты от пользователя по питомцу
     */
    @OneToMany(mappedBy = "adoptiveParent")
    private Collection<Report> reports;

    /**
     * Список всех животных одного хозяина
     */
    @OneToMany
    @JoinColumn(name = "adoptive_parent_id")
    private Collection<Pet> pets;
    /**
     * Сохраняем статус пользователя кошки, собаки
     */
    private TypeOfPet typeOfPet;

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
