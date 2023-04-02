package com.example.teamproject.service;

import com.example.teamproject.entities.UserContact;
import com.example.teamproject.repositories.UserContactRepository;
import org.springframework.stereotype.Service;

@Service
public class UserContactService {
    final UserContactRepository repository;

    public UserContactService(UserContactRepository repository) {
        this.repository = repository;
    }

    public void addUserContact(int chatId, String name, int phoneNumber) {
        repository.save(new UserContact(chatId, name, phoneNumber));

    }
}
