package com.example.teamproject.controllers;

import com.example.teamproject.entities.AdoptiveParent;
import com.example.teamproject.entities.Pet;
import com.example.teamproject.service.AdoptiveParentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.PostUpdate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/adoptive_parents")
public class AdoptiveParentController {


    private final AdoptiveParentService adoptiveParensService;

    public AdoptiveParentController(AdoptiveParentService adoptiveParensService) {
        this.adoptiveParensService = adoptiveParensService;
    }

    @Operation(
            summary = "Добавление усыновителя в базу",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Добавление усыновителя прошло успешно",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "415",
                            description = "Ошибка: Неверный формат",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @PostMapping()
    public AdoptiveParent saveAdoptiveParent(@Parameter(description = "Данные усыновителя") @RequestBody AdoptiveParent newAdoptiveParent) {
        return adoptiveParensService.save(newAdoptiveParent);
    }

    @Operation(
            summary = "Посмотреть всех усыновителей",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "415",
                            description = "Ошибка: Усыновителей не найдено",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )

    @GetMapping("/get_all")
    public ResponseEntity<Collection<AdoptiveParent>> getAllAdoptiveParents() {
        return ResponseEntity.ok(adoptiveParensService.findAll());
    }

    @Operation(
            summary = "Посмотреть усыновителя по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "415",
                            description = "Ошибка: Усыновитель не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )

    @GetMapping("/get_by_id")
    public ResponseEntity<AdoptiveParent> getAdoptiveParentById(@Parameter(description = "id усыновителя") @RequestParam Long id){
        return ResponseEntity.ok(adoptiveParensService.findAdoptiveParentById(id));
    }

    @Operation(
            summary = "Изменить усыновителя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "415",
                            description = "Ошибка: Усыновитель не найден или данные не корректны",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )

    @PutMapping
    public ResponseEntity<AdoptiveParent> updateAdoptiveParent(@Parameter(description = "Измененные данные усыновителя") @RequestBody AdoptiveParent adoptiveParent){
        return ResponseEntity.ok(adoptiveParensService.updateAdoptiveParent(adoptiveParent));
    }

    @Operation(
            summary = "Удалить усыновителя по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "415",
                            description = "Ошибка: Усыновитель не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )

    @DeleteMapping("{id}")
    public ResponseEntity deleteAdoptiveParentById(@Parameter(description = "id усыновителя") @PathVariable Long id){
        adoptiveParensService.deleteAdoptiveParentById(id);
        return ResponseEntity.ok().build();
    }
}
