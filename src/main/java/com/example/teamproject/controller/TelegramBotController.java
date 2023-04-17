package com.example.teamproject.controller;

import com.example.teamproject.entities.Pet;
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
@RequestMapping("/teamProject8")
public class TelegramBotController {
    //http://localhost:8080/teamProject8/get_all_pet
    private final PetService petService;

    public TelegramBotController(PetService petService) {
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
                            responseCode = "404",
                            description = "Загрузка питомца не прошла",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @PostMapping("/load_pet")
    public Pet loadPet(@Parameter(description = "кличка питомца") @RequestParam String name,
                       @Parameter(description = "вид животного") @RequestParam String type,
                       @Parameter(description = "возраст") @RequestParam int age,
                       @Parameter(description = "общая информация") @RequestParam String description){
        return petService.loadPet(name,type,age,description);
    }

    @Operation(
            summary = "Загрузка питомца в базу приюта",
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
    @GetMapping("/get_all_pet")
    public Collection<Pet> getAllPet(){
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
    @GetMapping("/get_pet")
    public Collection<Pet> getPet(@Parameter(description = "кличка питомца") @RequestParam String name){
        return petService.getPet(name);
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
    @DeleteMapping("/{id}/delete_pet")
    public ResponseEntity<Pet> deletePet(@Parameter(description = "id питомца") @PathVariable Long id){
        if (petService.deletePet(id) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(petService.deletePet(id));
    }
}
