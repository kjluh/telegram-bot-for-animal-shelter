package com.example.teamproject.listener;

import com.example.teamproject.entities.Report;
import com.example.teamproject.entities.TypeOfPet;
import com.example.teamproject.service.*;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    private AdoptiveParentService adoptiveParentService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private PetService petService;

    @Autowired
    private VolunteerService volunteerService;

    private int tempNumber;
    private Report tempReport = new Report();

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * Pattern для обработки сообщения от пользователя типа "89001122333 Имя вопрос"
     */
    private static final Pattern TELEPHONE_MESSAGE = Pattern.compile(
            "(\\d{11})(\\s)([А-яA-z)]+)(\\s)([А-яA-z)\\s\\d]+)"); // парсим сообщение на группы по круглым скобкам
    Long chatId;

    @Override
    public int process(List<Update> updates) {
        try {
            updates.forEach(update -> {
                logger.info("Processing update: {}", update);

                /**
                 * Отображение клавиатуры для пользователя с последующей обработкой кнопок
                 */
                if (update.callbackQuery() != null) {  // обработка этапа 0
                    chatId = update.callbackQuery().message().chat().id();
                    String data = update.callbackQuery().data();
                    switch (data) {

                        case "cat" -> {
                            adoptiveParentService.saveParentDataBase(chatId,TypeOfPet.CAT);
                            telegramBotService.firstMenu(chatId);
                        }
                        case "dog" -> {
                            adoptiveParentService.saveParentDataBase(chatId, TypeOfPet.DOG);
                            telegramBotService.firstMenu(chatId);
                        }
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

                        case "3" -> telegramBotService.sendReport(chatId);
                        case "Форма ежедневного отчёта" ->
                                telegramBot.execute(new SendMessage(chatId, "Форма ежедневного отчёта:\n1. Фото животного\n2. Рацион животного\n" +
                                        "3. Общее самочувствие и привыкание к новому месту \n4. Изменение в поведении"));
                        case "принимаем отчет" -> {
                            telegramBot.execute(new SendMessage(chatId, "Напишите Id животного, для которого составляется отчёт"));
                            tempNumber = 1;
                        }
                        case "позвать волонтера" ->
                                telegramBot.execute(volunteerService.sendMessageVolunteer(update.callbackQuery().message().from().username()));
                        case "записать данные" ->
                                telegramBot.execute(new SendMessage(chatId, "Введите номер телефона и вопрос в формате: 89001122333 Имя Ваш вопрос"));
                        case "Главное меню" -> {
                            telegramBotService.firstMenu(chatId);
                            telegramBotService.catOrDogMenu(chatId);
                        }
                    }
                    return;
                }
                /**
                 * Создание и получение данных о пользователе из чата.
                 */
                chatId = update.message().chat().id();

                /**
                 * Обработка сообщения от пользователя и вызов основного меню
                 */
                if ("/start".equals(update.message().text())) {  // этап 0
                    if (adoptiveParentService.findAdoptiveParentByChatId(chatId) == null) {
                        adoptiveParentService.saveParentDataBase(chatId);
                    }
                    telegramBotService.catOrDogMenu(chatId);
                }
                /**
                 * Поэтапное сохранение ежедневного отчёта о питомце
                 */
                else if (tempNumber == 1) {
                    tempReport.setPet(petService.getPetById(Long.valueOf(update.message().text())));
                    tempReport.setAdoptiveParent(adoptiveParentService.findAdoptiveParentByChatId(chatId));
                    telegramBot.execute(new SendMessage(chatId, "Теперь, пожалуйста, вышлите фото животного"));
                    tempNumber = 2;
                } else if (update.message().photo() != null && tempNumber == 2) { // проверяем что пришло фото
                    tempReport.setPhotoId((update.message().photo()[update.message().photo().length - 1]).fileId());
                    logger.info("Id photo {} ", tempReport.getPhotoId());
                    telegramBot.execute(new SendMessage(chatId, "Теперь, пожалуйста, пришлите рацион животного"));
                    tempNumber = 3;
                } else if (tempNumber == 3) {
                    tempReport.setDiet(update.message().text());
                    logger.info("Diet {} ", tempReport.getDiet());
                    telegramBot.execute(new SendMessage(chatId, "Теперь, пожалуйста, пришлите общее самочувствие и привыкание к новому месту"));
                    tempNumber = 4;
                } else if (tempNumber == 4) {
                    tempReport.setHealth(update.message().text());
                    logger.info("Health {} ", tempReport.getHealth());
                    telegramBot.execute(new SendMessage(chatId, "Теперь, пожалуйста, пришлите изменение в поведении: отказ от старых привычек, приобретение новых. Если такие имееются"));
                    tempNumber = 5;
                } else if (tempNumber == 5) {
                    tempReport.setBehavior(update.message().text());
                    logger.info("Behavior {} ", tempReport.getBehavior());
                    telegramBot.execute(new SendMessage(chatId, "Спасибо, информации принята!"));
                    tempNumber = 0;
                    tempReport.setReportDate(LocalDate.now());
                    reportService.loadReport(tempReport);

                }

                /**
                 * Проверяем сообщение пользователя на соответствие и сохраняем в БД,
                 * или выдаем информацию о несоответствии шаблону для сохранения.
                 */
                else if (update.message().text() != null) {
                    Matcher matcher = TELEPHONE_MESSAGE.matcher(update.message().text());
                    if (matcher.find()) {  //find запускает matcher
                        telegramBot.execute(adoptiveParentService.saveInfoDataBase(matcher, chatId));
                    } else {
                        telegramBot.execute(new SendMessage(chatId, "Некорректный формат номера телефона или сообщения"));
                    }
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
