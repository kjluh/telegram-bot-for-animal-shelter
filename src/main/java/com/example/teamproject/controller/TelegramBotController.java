package com.example.teamproject.controller;

import com.example.teamproject.entities.Pet;
import com.example.teamproject.service.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/teamProject8")
public class TelegramBotController {

    private final PetService petService;

    public TelegramBotController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping("/load_pet")
    public Pet loadPet(@RequestParam String name,
                       @RequestParam String type,
                       @RequestParam int age,
                       @RequestParam String description){
        return petService.loadPet(name,type,age,description);
    }

    @GetMapping("/get_all_pet")
    public Collection<Pet> getAllPet(){
        return petService.getAllPet();
    }

    @GetMapping("/get_pet")
    public Collection<Pet> getPet(@RequestParam String name){
        return petService.getPet(name);
    }

    @DeleteMapping("/id/delete_pet")
    public ResponseEntity<Pet> deletePet(@PathVariable Long id){
        if (petService.deletePet(id) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(petService.deletePet(id));
    }
}
