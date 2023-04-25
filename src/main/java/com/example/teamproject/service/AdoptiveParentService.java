package com.example.teamproject.service;

import com.example.teamproject.entities.AdoptiveParent;
import com.example.teamproject.entities.Pet;
import com.example.teamproject.entities.TypeOfPet;
import com.example.teamproject.repositories.AdoptiveParentRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.UUID;
import java.util.regex.Matcher;

@Service
public class AdoptiveParentService {

    @Autowired
    private AdoptiveParentRepository repository;

    /**
     * Метод сохранения запроса пользователя в БД использует метод {@link JpaRepository#save(Object)}
     *
     * @param chatId      принимает id чата
     * @param name        принимает имя пользователя
     * @param messageText принимает сообщение пользователя
     * @param phoneNumber принимает номер телефона пользователя
     */
    private void addUserContact(Long chatId, String name, String messageText, String phoneNumber) {
        AdoptiveParent adoptiveParent = new AdoptiveParent();
        adoptiveParent.setChatId(chatId);
        adoptiveParent.setName(name);
        adoptiveParent.setMessage(messageText);
        adoptiveParent.setPhoneNumber(Long.parseLong(phoneNumber));
        repository.save(adoptiveParent);
    }

    /**
     * Получить усыновителя со всеми питомцами по chatId
     *
     * @param chatId
     * @return AdoptiveParent
     */
    public AdoptiveParent findAdoptiveParentByChatId(Long chatId) {
        AdoptiveParent adoptiveParent = repository.findAdoptiveParentByChatId(chatId);
        return adoptiveParent;
    }

    /**
     * Получить усыновителя со всеми питомцами по id
     *
     * @param id
     * @return AdoptiveParent
     */
    public AdoptiveParent findAdoptiveParentById(Long id) {
        return repository.findById(id).get();
    }

    /**
     * @param matcher фильтр сообщения
     * @param chatId
     */
    public SendMessage saveInfoDataBase(Matcher matcher, Long chatId) {
        String phoneNumber = matcher.group(1); // получаем телефон
        String name = matcher.group(3); // получаем имя
        String messageText = matcher.group(5); // получаем текст сообщения
        addUserContact(chatId, name, messageText, phoneNumber); // создаем и пишем контакт в базу
        return new SendMessage(chatId, "Данные записаны, В ближайшее время мы с Вами свяжемся");
    }

    /**
     * Добавляем или обновляем в БД потенциального пользователя тип питомца по которому он обратился,
     * для отображения правильного меню
     * @param chatId ID чата пользователя
     * @param pet Тип питомца по которому обратился пользователь
     */
    public void saveParentDataBase(Long chatId, TypeOfPet pet) {
        AdoptiveParent adoptiveParent = findAdoptiveParentByChatId(chatId);
        if (adoptiveParent==null){
            adoptiveParent = new AdoptiveParent();
            adoptiveParent.setChatId(chatId);
            repository.save(adoptiveParent);
        }
        adoptiveParent.setTypeOfPet(pet);
        repository.save(adoptiveParent);
    }
}
