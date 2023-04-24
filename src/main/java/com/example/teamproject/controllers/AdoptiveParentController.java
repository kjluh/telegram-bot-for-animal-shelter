package com.example.teamproject.controllers;

import com.example.teamproject.entities.AdoptiveParent;
import com.example.teamproject.service.AdoptiveParentService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adoptive_parent")
public class AdoptiveParentController {

    private final AdoptiveParentService adoptiveParensService;

    public AdoptiveParentController(AdoptiveParentService adoptiveParensService) {
        this.adoptiveParensService = adoptiveParensService;
    }


    @GetMapping("/get_by_id")
    public ResponseEntity<AdoptiveParent> getAdoptiveParent(@Parameter(description = "id усыновителя") @RequestParam Long id){
        return ResponseEntity.ok(adoptiveParensService.findAdoptiveParentById(id));
    }
}
