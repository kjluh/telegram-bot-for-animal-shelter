package com.example.teamproject.service;

import com.example.teamproject.entities.AdoptiveParent;
import com.example.teamproject.entities.Pet;
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
    private TelegramBot telegramBot;

    @Autowired
    private AdoptiveParentRepository repository;

    @Autowired
    private PetService petService;

    /**
     * Метод сохранения запроса пользователя в БД использует метод {@link JpaRepository#save(Object)}
     *
     * @param chatId      принимает id чата
     * @param name        принимает имя пользователя
     * @param messageText принимает сообщение пользователя
     * @param phoneNumber принимает номер телефона пользователя
     */
    public void addUserContact(Long chatId, String name, String messageText, String phoneNumber) {
        AdoptiveParent adoptiveParent = new AdoptiveParent();
        adoptiveParent.setChatId(chatId);
        adoptiveParent.setName(name);
        adoptiveParent.setMessage(messageText);
        adoptiveParent.setPhoneNumber(Long.parseLong(phoneNumber));
        repository.save(adoptiveParent);
    }

    /**
     * Получить усыновителя со всеми питомцами
     * В работе использует метод findAllWhereParenId() из сервиса PetService
     *
     * @param chatId
     * @return AdoptiveParent
     */
    public AdoptiveParent findAdoptiveParentByChatId(Long chatId) {
        AdoptiveParent adoptiveParent = repository.findByChatId(chatId);
//        Collection<Pet> pets = petService.getPetsByParentId(adoptiveParent.getId());
//        adoptiveParent.setPets(pets);
        return adoptiveParent;
    }

    public void saveInfoDataBase(Matcher matcher, Long chatId) {
        try {
            String phoneNumber = matcher.group(1); // получаем телефон
            String name = matcher.group(3); // получаем имя
            String messageText = matcher.group(5); // получаем текст сообщения
            addUserContact(chatId, name, messageText, phoneNumber); // создаем и пишем контакт в базу
            SendMessage message = new SendMessage(chatId, "Данные записаны, В ближайшее время мы с Вами свяжемся");
            telegramBot.execute(message);
        } catch (RuntimeException e) {
            e.printStackTrace();
            SendMessage messageEx = new SendMessage(chatId, "Некорректный формат номера телефона или сообщения");
            telegramBot.execute(messageEx);
        }
    }

}
