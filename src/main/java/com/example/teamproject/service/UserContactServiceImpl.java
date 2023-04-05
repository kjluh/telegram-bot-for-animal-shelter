package com.example.teamproject.service;

import com.example.teamproject.entities.UserContact;
import com.example.teamproject.repositories.UserContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserContactServiceImpl{

    @Autowired
    private UserContactRepository repository;

    public void addUserContact(Long chatId, String name, String phoneNumber) {
        UserContact userContact = new UserContact();
        userContact.setChatId(chatId);
        userContact.setName(name);
        userContact.setPhoneNumber(Integer.parseInt(phoneNumber));
        repository.save(userContact);
    }
}
