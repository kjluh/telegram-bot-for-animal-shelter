package com.example.teamproject.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Сервис для приглашения волонтера в чат с клиентом
 */
@Service
public class VolunteerService {

    @Autowired
    private TelegramBot telegramBot;

    /**
     * Сюда нужно будет вписать ник в телеграме волонтера
     */
    String volunteerChat = "@volunteer";

    /**
     * Метод пригласить в текущий чат волонтера
     * @param chatId id текущего клиента
     */
    public void sendMessageVolunteer(Long chatId){
        telegramBot.execute(new SendMessage(chatId, "Вас просят присоединиться к чату " + volunteerChat));
    }
}
