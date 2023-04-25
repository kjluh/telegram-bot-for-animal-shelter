package com.example.teamproject.controllers;

import com.example.teamproject.entities.AdoptiveParent;
import com.example.teamproject.entities.Pet;
import com.example.teamproject.service.AdoptiveParentService;
import com.example.teamproject.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/pets")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @Operation(
            summary = "Загрузка питомца в базу приюта",
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
    public Pet loadPet(@Parameter(description = "Передаем питомца") @RequestBody Pet pet) {
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
    public Collection<Pet> getPet(@Parameter(description = "кличка питомца") @RequestParam String name) {
        return petService.getPet(name);
    }

    @Operation(
            summary = "Изменить питомца",
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
    public ResponseEntity<Pet> updatePet(@Parameter(description = "Измененные данные питомца") @RequestParam Pet pet){
        return ResponseEntity.ok(petService.updatePet(pet));
    }

    @Operation(
            summary = "Удаление питомца из приюта",
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
    public ResponseEntity<Pet> deletePet(@Parameter(description = "id питомца") @PathVariable Long id) {
        Pet pet = petService.getPetById(id);
        if (pet != null) {
            petService.deletePet(id);
            return ResponseEntity.ok(pet);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
