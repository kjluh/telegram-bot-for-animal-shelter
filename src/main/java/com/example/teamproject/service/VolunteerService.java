package com.example.teamproject.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Сервис для приглашения волонтера в чат с клиентом
 */
@Service
public class VolunteerService {
    /**
     * Сюда нужно будет вписать ник в телеграме волонтера
     */
    private String volunteerChat = "@ТУТ ДОЛЖЕН БЫТЬ АДРЕС ВОЛОНТЕРА!!!";

    public String getVolunteerChat() {
        return volunteerChat;
    }

    public void setVolunteerChat(String volunteerChat) {
        this.volunteerChat = volunteerChat;
    }

    /**
     * Метод пригласить в текущий чат волонтера
     *
     * @param user имя текущего клиента текущего клиента
     */
    public SendMessage sendMessageVolunteer(String user) {
              return new SendMessage(volunteerChat, "Вас просят присоединиться к чату " + "@" + user);
    }
}
