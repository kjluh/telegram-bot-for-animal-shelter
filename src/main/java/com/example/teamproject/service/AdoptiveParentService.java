package com.example.teamproject.service;

import com.example.teamproject.entities.AdoptiveParent;
import com.example.teamproject.entities.TypeOfPet;
import com.example.teamproject.repositories.AdoptiveParentRepository;
import com.example.teamproject.utils.Util;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.regex.Matcher;

@Service
public class AdoptiveParentService {

    @Autowired
    private AdoptiveParentRepository repository;

    /**
     * Метод сохранения запроса пользователя в БД использует метод {@link JpaRepository#save(Object)}
     *
     * @param chatId      принимает id чата
     * @param name        принимает имя пользователя
     * @param messageText принимает сообщение пользователя
     * @param phoneNumber принимает номер телефона пользователя
     */
    private void addUserContact(Long chatId, String name, String messageText, String phoneNumber) {

        AdoptiveParent adoptiveParent = findAdoptiveParentByChatId(chatId);
        if (adoptiveParent==null){
            adoptiveParent = new AdoptiveParent();
            adoptiveParent.setChatId(chatId);
        }
        adoptiveParent.setName(name);
        adoptiveParent.setMessage(messageText);
        adoptiveParent.setPhoneNumber(Long.parseLong(phoneNumber));
        repository.save(adoptiveParent);
    }

    /**
     * Получить усыновителя со всеми питомцами по chatId
     *
     * @param chatId
     * @return AdoptiveParent
     */
    public AdoptiveParent findAdoptiveParentByChatId(Long chatId) {
        AdoptiveParent adoptiveParent = repository.findAdoptiveParentByChatId(chatId);
        return adoptiveParent;
    }

    /**
     * Получить усыновителя со всеми питомцами по id
     *
     * @param id
     * @return AdoptiveParent
     */
    public AdoptiveParent findAdoptiveParentById(Long id) {
        return repository.findById(id).get();
    }

    /**
     * @param matcher фильтр сообщения
     * @param chatId
     */
    public SendMessage saveInfoDataBase(Matcher matcher, Long chatId) {
        String phoneNumber = matcher.group(1); // получаем телефон
        String name = matcher.group(3); // получаем имя
        String messageText = matcher.group(5); // получаем текст сообщения
        addUserContact(chatId, name, messageText, phoneNumber); // создаем и пишем контакт в базу
        return new SendMessage(chatId, "Данные записаны, В ближайшее время мы с Вами свяжемся");
    }

    /**
     * Добавляем или обновляем в БД потенциального пользователя тип питомца по которому он обратился,
     * для отображения правильного меню
     * @param chatId ID чата пользователя
     * @param pet Тип питомца по которому обратился пользователь
     */
    public void saveParentDataBase(Long chatId, TypeOfPet pet) {
        AdoptiveParent adoptiveParent = findAdoptiveParentByChatId(chatId);
        if (adoptiveParent == null){
            adoptiveParent = new AdoptiveParent();
            adoptiveParent.setChatId(chatId);
            repository.save(adoptiveParent);
        }
        adoptiveParent.setTypeOfPet(pet);
        repository.save(adoptiveParent);
    }

    /**
     * Найти всех усыновителей со всеми питомцами
     *
     * @return Все усыновители с питомцами
     */
    public Collection<AdoptiveParent> findAll() {
        return repository.findAll();
    }

    /**
     * Сохранить в БД нового усыновителя
     *
     * @param newAdoptiveParent Сущность нового усыновителя
     * @return Возвращаем добавленную сущность в качестве положительного ответа
     */
    public AdoptiveParent save(AdoptiveParent newAdoptiveParent) {
        return repository.save(newAdoptiveParent);
    }

    /**
     * Обновление информации по усыновителю
     *
     * @param adoptiveParent Обновленная информация по усыновителю
     * @return Возвращаем обновленную сущность в качестве положительного ответа
     */
    public AdoptiveParent updateAdoptiveParent(AdoptiveParent adoptiveParent) {
        return repository.save(adoptiveParent);
    }

    /**
     * Удаление усыновителя из БД
     *
     * @param id id усыновителя
     */
    public void deleteAdoptiveParentById(Long id) {
        repository.deleteById(id);
    }

    public void saveReportStatus(Long chatId, Util.ReportStatus status) {
        AdoptiveParent adoptiveParent = findAdoptiveParentByChatId(chatId);
        adoptiveParent.setReportStatus(status);
        save(adoptiveParent);
    }
}
