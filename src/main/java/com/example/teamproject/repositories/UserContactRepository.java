package com.example.teamproject.repositories;


import com.example.teamproject.entities.UserContact;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для хранения контактных данных пользователя, использует {@link JpaRepository#save(Object)}
 */
public interface UserContactRepository extends JpaRepository<UserContact, Long> {

}
