package com.example.teamproject.service;

import com.example.teamproject.entities.AdoptiveParent;
import com.example.teamproject.entities.Pet;
import com.example.teamproject.entities.Report;
import com.example.teamproject.repositories.AdoptiveParentRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
@EnableScheduling
public class ReminderForParents {

    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private AdoptiveParentRepository adoptiveParentRepository;

    /**
     * Метод каждый день в 23:59 проверяет - отправлял ли пользователь отчет о питомце, если нет напоминает ему.
     * Если отчет не высылался 2 дня пишет наставнику.
     */
    @Scheduled(cron = "* 59 23 * * *")
    public void checkForReports() {
        List<AdoptiveParent> adoptiveParents = adoptiveParentRepository.findAll(); // получаем всех усыновителей
        for (AdoptiveParent aPs : adoptiveParents) { //идем по списку усыновителей
            Collection<Report> reports = aPs.getReports(); // получаем список всех отчетов о питомцев от одного усыновителя
            LocalDate localDate = reports.stream().map(Report::getReportDate).max(LocalDate::compareTo).orElse(null);
            if (!localDate.equals(LocalDate.now())) {
                telegramBot.execute(new SendMessage(aPs.getChatId(), "ВЫ Не Отправили сегодня сообщение о состоянии питомца"));
                return;
            }
            if (!localDate.equals(LocalDate.now().minusDays(1)) && !localDate.equals(LocalDate.now())) {
                telegramBot.execute(new SendMessage(volunteerService.getVolunteerChat(), "Пользователь" + aPs + " не отправляет отчеты"));
            }
        }
    }

    /**
     * Метод каждый день в 10 00 проверяет в базе всех усыновителей и поздравляет тех чей испытательный срок кончился.
     */
    @Scheduled(cron = "* 00 10 * * *")
    public void checkTrialPeriod() {
        List<AdoptiveParent> adoptiveParents = adoptiveParentRepository.findAll(); // получаем всех усыновителей
        for (AdoptiveParent aPs : adoptiveParents) {
            Collection<Pet> pets = aPs.getPets();
            for (Pet petNow : pets) {
                if (petNow.getTrialPeriod().equals(LocalDate.now())) {
                    telegramBot.execute(new SendMessage(aPs.getChatId(), "Поздравляем с завершением испытательного срока"));
                }
            }
        }
    }
}