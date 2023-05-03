package com.example.teamproject.service;

import com.example.teamproject.entities.AdoptiveParent;
import com.example.teamproject.entities.Pet;
import com.example.teamproject.entities.Report;
import com.example.teamproject.listener.TelegramBotUpdatesListener;
import com.example.teamproject.repositories.AdoptiveParentRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ReminderForParentsTest {

    @MockBean
    private TelegramBot telegramBot;

    @MockBean
    private TelegramBotUpdatesListener telegramBotUpdatesListener;

    @Autowired
    private AdoptiveParentRepository adoptiveParentRepository;

    @Autowired
    private ReminderForParents reminderForParents;

    @Autowired
    private VolunteerService volunteerService;

    @Test
    void checkForReportsTest() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        AdoptiveParent adoptiveParent = new AdoptiveParent();
        Report report = new Report();
        Pet pet = new Pet();
        adoptiveParent.setId(1L);
        adoptiveParent.setName("test");
        adoptiveParent.setChatId(1L);
        pet.setId(1L);
        pet.setName("testPet");
        pet.setAdoptiveParent(adoptiveParent);
        report.setId(1L);
        report.setReportDate(LocalDate.now().minusDays(1));
        report.setPet(pet);
        report.setAdoptiveParent(adoptiveParent);
        when(chat.id()).thenReturn(adoptiveParent.getChatId());
        when(message.chat()).thenReturn(chat);
        when(update.message()).thenReturn(message);
        Collection<Report> reports = new ArrayList<>();
        reports.add(report);
        adoptiveParent.setReports(reports);
        adoptiveParentRepository.save(adoptiveParent);

        reminderForParents.checkForReports();

//        adoptiveParents = adoptiveParentRepository.findAll(); // получаем всех усыновителей
//        for (AdoptiveParent aPs : adoptiveParents) { //идем по списку усыновителей
//             reports = aPs.getReports(); // получаем список всех отчетов о питомцев от одного усыновителя
//            LocalDate localDate = reports.stream().map(Report::getReportDate).max(LocalDate::compareTo).orElse(null);
//            if (!localDate.equals(LocalDate.now())) {
//                telegramBot.execute(new SendMessage(aPs.getChatId(), "ВЫ Не Отправили сегодня сообщение о состоянии питомца"));
//                return;
//            }
//            if (!localDate.equals(LocalDate.now().minusDays(1)) && !localDate.equals(LocalDate.now())) {
//                telegramBot.execute(new SendMessage(volunteerService.getVolunteerChat(), "Пользователь" + aPs + " не отправляет отчеты"));
//            }
//        }

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        when(telegramBot.execute(argumentCaptor.capture())).thenReturn(null);
        telegramBotUpdatesListener.process(List.of(update));
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(adoptiveParent.getChatId());
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo("ВЫ Не Отправили сегодня сообщение о состоянии питомца");
    }
}
