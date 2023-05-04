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

    @MockBean
    private AdoptiveParentRepository adoptiveParentRepository;

    @Autowired
    private ReminderForParents reminderForParents;

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
        List<AdoptiveParent> adoptiveParents = new ArrayList<>();
        adoptiveParents.add(adoptiveParent);
        when(adoptiveParentRepository.save(adoptiveParent)).thenReturn(adoptiveParent);
        when(adoptiveParentRepository.findAll()).thenReturn(adoptiveParents);

        reminderForParents.checkForReports();

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        when(telegramBot.execute(argumentCaptor.capture())).thenReturn(null);
        telegramBotUpdatesListener.process(List.of(update));
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(adoptiveParent.getChatId());
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo("ВЫ Не Отправили сегодня сообщение о состоянии питомца");
    }
}
