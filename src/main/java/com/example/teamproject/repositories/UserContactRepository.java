package com.example.teamproject.repositories;


import com.example.teamproject.entities.UserContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserContactRepository extends JpaRepository<UserContact, Long> {

}
