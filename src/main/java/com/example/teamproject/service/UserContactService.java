package com.example.teamproject.service;

import com.example.teamproject.entities.UserContact;
import com.example.teamproject.repositories.UserContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserContactService {

    @Autowired
    private UserContactRepository repository;

    /**
     * Метод сохранения запроса пользователя в БД
     * @param chatId принимает id чата
     * @param name принимает имя пользователя
     * @param messageText принимает сообщение пользователя
     * @param phoneNumber принимает номер телефона пользователя
     */
    public void addUserContact(Long chatId, String name, String messageText, String phoneNumber) {
        UserContact userContact = new UserContact();
        userContact.setChatId(chatId);
        userContact.setName(name);
        userContact.setMessage(messageText);
        userContact.setPhoneNumber(Long.parseLong(phoneNumber));
        repository.save(userContact);
    }
}
