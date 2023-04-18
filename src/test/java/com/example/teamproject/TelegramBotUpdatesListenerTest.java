package com.example.teamproject;

import com.example.teamproject.listener.TelegramBotUpdatesListener;
import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TelegramBotUpdatesListenerTest {

    @MockBean
    private TelegramBot telegramBot;

    @Autowired
    private TelegramBotUpdatesListener telegramBotUpdatesListener;


    @Test
    public void handlerStartMessageTest() {
        String inputMessage = "/start";
        String outMessage = "Привет,  тут должна быть информация о боте. Выберите интересующий пункт из меню: ";
        Assertions.assertThat(testHandler(inputMessage).getParameters().get("chat_id")).isEqualTo(123L);
        Assertions.assertThat(testHandler(inputMessage).getParameters().get("text")).isEqualTo(outMessage);
    }

    @Test
    public void handleStartTest() {
        String inputMessage = "инфа о приюте";
        String outMessage = "тут должна быть информация о приюте.";
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(chat.id()).thenReturn(123L);
        when(message.text()).thenReturn("инфа о приюте");
        when(message.chat()).thenReturn(chat);
        when(update.message()).thenReturn(message);
        when(update.callbackQuery().data()).thenReturn(inputMessage);

        Assertions.assertThat(update.callbackQuery().id()).isEqualTo(123L);
        Assertions.assertThat(testHandler(outMessage).getParameters().get("text")).isEqualTo(outMessage);
    }


    private SendMessage testHandler(String testMessage) {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(chat.id()).thenReturn(123L);
        when(message.text()).thenReturn(testMessage);
        when(message.chat()).thenReturn(chat);
        when(update.message()).thenReturn(message);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        when(telegramBot.execute(argumentCaptor.capture())).thenReturn(null);
        telegramBotUpdatesListener.process(List.of(update));
        SendMessage actual = argumentCaptor.getValue();
        return actual;
    }
}
