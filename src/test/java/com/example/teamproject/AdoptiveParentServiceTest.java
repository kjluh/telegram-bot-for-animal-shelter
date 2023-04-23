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
import org.springframework.boot.test.mock.mockito.MockBean;

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

    @MockBean
    TelegramBot telegramBot;


    //НЕ РАБОТАЕТ!!!!
    @Test
    public void saveInfoDataBaseTest(){
        final Pattern TELEPHONE_MESSAGE = Pattern.compile(
                "(\\d{11})(\\s)([А-яA-z)]+)(\\s)([А-яA-z)\\s\\d]+)");
        Matcher matcher = TELEPHONE_MESSAGE.matcher("89991122333 name text message");

        adoptiveParentService.saveInfoDataBase(matcher,123L);

        Assertions.assertThat(adoptiveParentService.findAdoptiveParentByChatId(123L).getChatId()).isEqualTo(123L);
        Assertions.assertThat(adoptiveParentService.findAdoptiveParentByChatId(123L).getName()).isEqualTo("name");
        Assertions.assertThat(adoptiveParentService.findAdoptiveParentByChatId(123L).getPhoneNumber()).isEqualTo(89991122333L);
    }

}
