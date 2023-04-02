package com.example.teamproject.service;

import com.example.teamproject.entities.UserContact;
import com.example.teamproject.repositories.UserContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserContactService {
    @Autowired
    private UserContactRepository repository;

//    public UserContactService(UserContactRepository repository) {
//        this.repository = repository;
//    }

    public void addUserContact(Long chatId, String name, int phoneNumber) {
        repository.save(new UserContact(chatId, name, phoneNumber));

    }
}
