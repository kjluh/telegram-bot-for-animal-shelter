package com.example.teamproject.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    @Autowired
    private TelegramBot telegramBot;

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        try {
            updates.forEach(update -> {
                logger.info("Processing update: {}", update);

                if (update.callbackQuery() != null) {  // обработка этапа 0
                    Long chatId = update.callbackQuery().message().chat().id();
                    CallbackQuery callbackQuery = update.callbackQuery();
                    String data = callbackQuery.data();
                    switch (data) {
                        case "1" -> shelterInfo(chatId, update);
                        case "инфа о приюте" ->
                                telegramBot.execute(new SendMessage(chatId, "тут должна быть информация о приюте"));
                        case "расписание работы" ->
                                telegramBot.execute(new SendMessage(chatId, " тут должно быть расписание работы приюта и адрес, схему проезда"));
                        case "рекомендации о ТБ" ->
                                telegramBot.execute(new SendMessage(chatId, "тут должны быть общие рекомендации о технике безопасности на территории приюта"));
                        case "записать данные" ->
                                telegramBot.execute(new SendMessage(chatId, "тут должен быть метод записать данные"));
                        case "2" -> telegramBot.execute(new SendMessage(chatId, "что-то делаем2"));
                        case "3" -> telegramBot.execute(new SendMessage(chatId, "что-то делаем3"));
                        case "4" -> telegramBot.execute(new SendMessage(chatId, "что-то делаем4"));
                    }
                    return;
                }


                User user = update.message().from();
                Long chatId = user.id();

                if (update.message().text().equals("/start")) {  // этап 0
                    SendMessage helloMessage = new SendMessage(chatId, "Привет,  тут должна быть информация о боте. Выберите интересующий пункт из меню: ");

                    InlineKeyboardButton button1 = new InlineKeyboardButton("Узнать информацию о приюте!");
                    button1.callbackData("1");
                    InlineKeyboardButton button2 = new InlineKeyboardButton("Как взять собаку из приюта?");
                    button2.callbackData("2");
                    InlineKeyboardButton button3 = new InlineKeyboardButton("Прислать отчет о питомце");
                    button3.callbackData("3");
                    InlineKeyboardButton button4 = new InlineKeyboardButton("Позвать волонтера");
                    button4.callbackData("4");
                    InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
                    keyboard.addRow(button1, button2);
                    keyboard.addRow(button3, button4);
                    helloMessage.replyMarkup(keyboard);
                    telegramBot.execute(helloMessage);
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public void shelterInfo(Long chatId, Update update) { // кнопки этапа 1

        SendMessage message = new SendMessage(chatId, "Приветствует в нашем приюте");

        InlineKeyboardButton button1 = new InlineKeyboardButton("инфа о приюте");
        button1.callbackData("инфа о приюте");
        InlineKeyboardButton button2 = new InlineKeyboardButton("расписание работы");
        button2.callbackData("расписание работы");
        InlineKeyboardButton button3 = new InlineKeyboardButton("рекомендации о ТБ");
        button3.callbackData("рекомендации о ТБ");
        InlineKeyboardButton button4 = new InlineKeyboardButton("записать данные");
        button4.callbackData("записать данные");
        InlineKeyboardButton button5 = new InlineKeyboardButton("Позвать волонтера");
        button5.callbackData("4");
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        keyboard.addRow(button1);
        keyboard.addRow(button2);
        keyboard.addRow(button3);
        keyboard.addRow(button4);
        keyboard.addRow(button5);
        message.replyMarkup(keyboard);
        telegramBot.execute(message);
    }

}
