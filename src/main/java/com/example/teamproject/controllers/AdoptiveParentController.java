package com.example.teamproject.controllers;

import com.example.teamproject.entities.AdoptiveParent;
import com.example.teamproject.entities.Pet;
import com.example.teamproject.service.AdoptiveParentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    public ResponseEntity<Collection<AdoptiveParent>> getAll() {
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
    public ResponseEntity<AdoptiveParent> getById(@Parameter(description = "id усыновителя") @RequestParam Long id){
        return ResponseEntity.ok(adoptiveParensService.findAdoptiveParentById(id));
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
}
