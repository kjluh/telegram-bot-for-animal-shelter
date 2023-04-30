package com.example.teamproject.repositories;

import com.example.teamproject.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface PetRepository extends JpaRepository<Pet, Long> {

    Collection<Pet> findByNameIgnoreCase(String name);

//    Collection<Pet> findAllByParentId(Long id);
}
