package com.example.teamproject.controllers;

import com.example.teamproject.entities.Pet;
import com.example.teamproject.service.AdoptiveParentService;
import com.example.teamproject.service.PetService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@RestController
@RequestMapping("/pets")
public class PetController {
    private TelegramBot telegramBot;
    private final PetService petService;
    private final AdoptiveParentService adoptiveParentService;

    public PetController(PetService petService, AdoptiveParentService adoptiveParentService, TelegramBot telegramBot) {
        this.petService = petService;
        this.adoptiveParentService = adoptiveParentService;
        this.telegramBot = telegramBot;
    }

    @Operation(
            summary = "Загрузка питомца в базу приюта",
            description = "параметры питомца",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Загрузка питомца прошла успешно",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "415",
                            description = "Загрузка питомца не прошла, неверный формат",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @PostMapping()
    public Pet loadPet(@RequestBody Pet pet) {
        return petService.loadPet(pet);
    }


    @Operation(
            summary = "Получение всех животных приюта",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Все животные приюта",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet[].class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Что-то пошло не так:)"
                    )
            }
    )
    @GetMapping()
    public Collection<Pet> getAllPet() {
        return petService.getAllPet();
    }

    @Operation(
            summary = "Поиск питомца по имени",
            description = "кличка питомца",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "список всех питомцев с именем в поиске",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet[].class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Что-то пошло не так:)"
                    )
            }
    )
    @GetMapping("/")
    public Collection<Pet> getPet(@RequestParam String name) {
        return petService.getPet(name);
    }

    @Operation(
            summary = "Изменить питомца",
            description = "Параметры питомца",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "415",
                            description = "Ошибка: Питомец не найден или данные не корректны",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )

    @PutMapping
    public ResponseEntity<Pet> updatePet(@RequestBody Pet pet) {
        Pet pet1 = petService.getPetById(pet.getId());
        if (pet1 != null) {
            return ResponseEntity.ok(petService.updatePet(pet));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Изменить дату испытательного срока у питомца",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Дата успешно изменена",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "415",
                            description = "Ошибка: Питомец не найден или данные не корректны",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )

    @PutMapping("/{id}/{date}")
    public ResponseEntity<Pet> updatePetTrialPeriodById(@Parameter(description = "id питомца") @PathVariable Long id,
                                                        @Parameter(description = "Новая дата в формате день.месяц.год (15.01.2023)") @PathVariable String date) {
        Pet pet = petService.getPetById(id);
        if (pet == null) {
            return ResponseEntity.status(404).build();
        }//Вытаскиваем питомца из базы по id
        if (date.matches("[0-3]\\d\\.[0-1]\\d\\.[2-9]\\d\\d\\d")) { //Проверем дату по регулярному выражению
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy"); //Создаем паттерн для парсинга строки в дату
            LocalDate realDate = LocalDate.parse(date, formatter); // переводим строку в дату
            pet.setTrialPeriod(realDate); // Меняем дату
            if (pet.getAdoptiveParent() != null) {
                telegramBot.execute(new SendMessage(adoptiveParentService.findAdoptiveParentById(pet.getId()).getChatId(),
                        "Ваш испытательный срок изменился и закончится " + realDate));
            }
            return ResponseEntity.ok(petService.updatePet(pet)); // Сохраняем с новой датой
        } else {
            return ResponseEntity.status(415).build(); // Если дата не верно написана, то возвращаем ошибку
        }
    }

    @Operation(
            summary = "Удаление питомца из приюта",
            description = "id питомца",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаление питомца из приюта прошло успешно",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "Not Found",
                            description = "Удаление питомца из приюта не прошло, питомца там уже нет.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Pet> deletePet(@PathVariable Long id) {
        Pet pet = petService.getPetById(id);
        if (pet != null) {
            petService.deletePet(id);
            return ResponseEntity.ok(pet);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
