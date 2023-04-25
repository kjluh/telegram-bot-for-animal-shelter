package com.example.teamproject.service;

import com.example.teamproject.entities.Pet;
import com.example.teamproject.repositories.PetRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    /**
     * Метод Загружает животное в БД
     * @param newPet в формате JSON  клас PET
     * @return возвращает загруженное животное
     */
    public Pet loadPet(Pet newPet) {
        petRepository.save(newPet);
        return newPet;
    }

    /**
     * Получаем список всех животных в приюте
     * @return list объектов класса PET из БД
     */
    public Collection<Pet> getAllPet() {
        return petRepository.findAll();
    }

    /**
     * Получаем список всех животных в приюте с определенным именем
     * @param name имя для поиска в БД
     * @return list объектов класса PET с заданным именем из БД
     */
    public Collection<Pet> getPet(String name) {
        return petRepository.findByNameIgnoreCase(name);
    }

    /**
     * Поиск питомца по его ID
     * @param id ID питомца из БД
     * @return возвращаем полученного питомца
     */
    public Pet getPetById(Long id){
        return petRepository.findById(id).orElse(null);
    }

    /**
     * Удаляем животное из БД
     * @param id ID для удаления
     * @return возвращаем удаленное животное
     */
    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }


}
