package com.example.teamproject;

import com.example.teamproject.listener.TelegramBotUpdatesListener;
import com.example.teamproject.service.AdoptiveParentService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.SecureRandom;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SpringBootTest
public class AdoptiveParentServiceTest {

    @Autowired
    AdoptiveParentService adoptiveParentService;

    @Autowired
    private TelegramBotUpdatesListener telegramBotUpdatesListener;

    @Autowired
    TelegramBot telegramBot;


    //НЕ РАБОТАЕТ!!!!
    @Test
    public void saveInfoDataBaseTest(){
        final Pattern TELEPHONE_MESSAGE = Pattern.compile(
                "(\\d{11})(\\s)([А-яA-z)]+)(\\s)([А-яA-z)\\s\\d]+)");
        Matcher matcher = TELEPHONE_MESSAGE.matcher("89991122333 name text message");

        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(chat.id()).thenReturn(123L);
        when(message.text()).thenReturn("89991122333 name text message");
        when(message.chat()).thenReturn(chat);
        when(update.message()).thenReturn(message);

        adoptiveParentService.saveInfoDataBase(matcher,123L);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        when(telegramBot.execute(argumentCaptor.capture())).thenReturn(null);
        telegramBotUpdatesListener.process(List.of(update));
        SendMessage actual = argumentCaptor.getValue();


        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(123L);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo("Данные записаны, В ближайшее время мы с Вами свяжемся");
    }

}
