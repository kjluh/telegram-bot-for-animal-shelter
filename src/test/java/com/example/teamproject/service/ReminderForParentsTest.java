package com.example.teamproject.service;

import com.example.teamproject.entities.AdoptiveParent;
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
    private AdoptiveParent adoptiveParent;

    @MockBean
    private AdoptiveParentRepository adoptiveParentRepository;

//    @Test
//    void checkForReportsTest() {
//        Report report = mock(Report.class);
//        Update update = mock(Update.class);
//        Message message = mock(Message.class);
//        Chat chat = mock(Chat.class);
//        when(chat.id()).thenReturn(123L);
//        when(message.text()).thenReturn("1 name text message");
//        when(message.chat()).thenReturn(chat);
//        when(update.message()).thenReturn(message);
//        report.setReportDate(LocalDate.now());
//        List<Report> reports = new ArrayList<>();
//        reports.add(report);
//        when(adoptiveParent.getReports()).thenReturn(reports);
//        List<AdoptiveParent> adoptiveParents = new ArrayList<>();
//        adoptiveParents.add(adoptiveParent);
//        when(adoptiveParentRepository.findAll()).thenReturn(adoptiveParents);
//
//        ReminderForParents.checkForReports();
//
//        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
//        when(telegramBot.execute(argumentCaptor.capture())).thenReturn(null);
//        telegramBotUpdatesListener.process(List.of(update));
//        SendMessage actual = argumentCaptor.getValue();
//
//        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(123L);
//        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo("Некорректный формат номера телефона или сообщения");
//    }
}
