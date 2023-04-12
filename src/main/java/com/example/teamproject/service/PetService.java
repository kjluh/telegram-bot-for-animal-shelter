package com.example.teamproject.service;

import com.example.teamproject.entities.Pet;
import com.example.teamproject.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PetService {


    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet loadPet(String name, String type, int age, String description) {
        Pet newPet = new Pet();
        newPet.setName(name);
        newPet.setType(type);
        newPet.setAge(age);
        newPet.setDescription(description);
        petRepository.save(newPet);
        return newPet;
    }

    public Collection<Pet> getAllPet() {
        return petRepository.findAll();
    }

    public Collection<Pet> getPet(String name) {
        return petRepository.findByNameIgnoreCase(name);
    }

    public Pet deletePet(Long id) {
        Pet pet = petRepository.findById(id).orElseThrow();
        petRepository.deleteById(id);
        return pet;
    }
//
//    public Collection<Pet> getPetsByParentId(Long id) {
//        return petRepository.findAllByParentId(id);
//    }
}
