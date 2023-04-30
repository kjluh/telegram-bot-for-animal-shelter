package com.example.teamproject.repositories;

import com.example.teamproject.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface PetRepository extends JpaRepository<Pet, Long> {

    Collection<Pet> findByNameIgnoreCase(String name);

    @Query(value = "SELECT adoptive_parent_id FROM pet WHERE id = :id", nativeQuery = true)
    Long findAdoptiveParentIdByPetId(Long id);

//    Collection<Pet> findAllByParentId(Long id);
}
