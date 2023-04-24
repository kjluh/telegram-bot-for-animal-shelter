package com.example.teamproject.controller;

import com.example.teamproject.entities.AdoptiveParent;
import com.example.teamproject.service.AdoptiveParentService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

public class AdoptiveParentController {


    private final AdoptiveParentService adoptiveParensService;

    public AdoptiveParentController(AdoptiveParentService adoptiveParensService) {
        this.adoptiveParensService = adoptiveParensService;
    }

    @GetMapping("/get_all")
    public ResponseEntity<Collection<AdoptiveParent>> getAll() {
        return ResponseEntity.ok(adoptiveParensService.findAll());
    }

    @GetMapping("/get_by_id")
    public ResponseEntity<AdoptiveParent> getById(@Parameter(description = "id усыновителя") @RequestParam Long id){
        return ResponseEntity.ok(adoptiveParensService.findAdoptiveParentById(id));
    }
}
