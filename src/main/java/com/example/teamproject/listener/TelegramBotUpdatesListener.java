package com.example.teamproject.listener;

import com.example.teamproject.service.TelegramBotService;
import com.example.teamproject.service.UserContactService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    @Autowired
    private TelegramBot telegramBot;

    @Autowired
   private TelegramBotService telegramBotService;

    @Autowired
    private UserContactService userContactService;

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }
    private static final Pattern TELEPHONE_MESSAGE = Pattern.compile(
            "(\\d{11})(\\s)([А-яA-z)]+)(\\s)([А-яA-z)\\s\\d]+)"); // парсим сообщение на группы по круглым скобкам
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

                        case "1" -> telegramBotService.shelterInfo(chatId);
                        case "инфа о приюте" ->
                                telegramBot.execute(new SendMessage(chatId, "тут должна быть информация о приюте."));
                        case "расписание работы" ->
                                telegramBot.execute(new SendMessage(chatId, " тут должно быть расписание работы приюта и адрес, схему проезда."));
                        case "рекомендации о ТБ" ->
                                telegramBot.execute(new SendMessage(chatId, "тут должны быть общие рекомендации о технике безопасности на территории приюта."));

                        case "2" -> telegramBotService.takeDogFromShelter(chatId);
                        case "Правила знакомства" ->
                                telegramBot.execute(new SendMessage(chatId, " тут должны быть правила знакомства с собакой до того, как можно забрать ее из приюта."));
                        case "Список документов" ->
                                telegramBot.execute(new SendMessage(chatId, " тут должен быть список документов, необходимых для того, чтобы взять собаку из приюта."));
                        case "транспортировка животного" ->
                                telegramBot.execute(new SendMessage(chatId, " тут должно быть список рекомендаций по транспортировке животного."));
                        case "дома для щенка" ->
                                telegramBot.execute(new SendMessage(chatId, " тут должно быть список рекомендаций по обустройству дома для щенка."));
                        case "дома для собаки" ->
                                telegramBot.execute(new SendMessage(chatId, " тут должно быть список рекомендаций по обустройству дома для взрослой собаки."));
                        case "дома для собаки с ограничениями" ->
                                telegramBot.execute(new SendMessage(chatId, " тут должен быть список рекомендаций по обустройству дома для собаки с ограниченными возможностями (зрение, передвижение)."));
                        case "советы кинолога" ->
                                telegramBot.execute(new SendMessage(chatId, " тут должны быть советы кинолога по первичному общению с собакой"));
                        case "список кинологов" ->
                                telegramBot.execute(new SendMessage(chatId, " тут должны быть рекомендации по проверенным кинологам для дальнейшего обращения к ним."));
                        case "список причин для отказа" ->
                                telegramBot.execute(new SendMessage(chatId, " тут должен быть список причин, почему могут отказать и не дать забрать собаку из приюта."));

                        case "3" -> telegramBot.execute(new SendMessage(chatId, "что-то делаем3"));
                        case "позвать волонтера" -> telegramBot.execute(new SendMessage(chatId, "Зовем волонтера"));
                        case "записать данные" -> {
                            telegramBot.execute(new SendMessage(chatId, "Введите номер телефона и вопрос в формате: 89001122333 Ваш вопрос."));
                            Matcher matcher = TELEPHONE_MESSAGE.matcher(data);
                            if (matcher.find()) {  //find запускает matcher
                                try {
                                    Integer telephone = Integer.valueOf(matcher.group(1)); // получаем телефон
                                    String name = matcher.group(3); // получаем имя
                                    String messageText = matcher.group(5); // получаем текст сообщения
                                    userContactService.addUserContact(chatId,name,telephone); // создаем и пишем контакт в базу
                                    SendMessage message = new SendMessage(chatId, "Данные записаны, В ближайшее время мы с Вами свяжемся");
                                    telegramBot.execute(message);
                                } catch (DateTimeParseException e) {
                                    SendMessage messageEx = new SendMessage(chatId, "Некорректный формат номера телефона или сообщения");
                                    telegramBot.execute(messageEx);
                                }
                            }
                        }

                    }
                    return;
                }


                User user = update.message().from();
                Long chatId = user.id();

                if (update.message().text().equals("/start")) {  // этап 0
                    telegramBotService.firstMenu(chatId);
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
