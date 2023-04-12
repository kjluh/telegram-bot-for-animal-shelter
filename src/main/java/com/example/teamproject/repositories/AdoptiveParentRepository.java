package com.example.teamproject.repositories;


import com.example.teamproject.entities.AdoptiveParent;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для хранения контактных данных пользователя, использует {@link JpaRepository#save(Object)}
 */
public interface AdoptiveParentRepository extends JpaRepository<AdoptiveParent, Long> {

    AdoptiveParent findByChatId(Long chatId);
}
