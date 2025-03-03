package com.example.project.controller;

import com.example.project.dto.PetDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import com.example.project.entities.Pet;
import com.example.project.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class petController {

    @Autowired
    private PetService petService;

    @PostMapping
    public ResponseEntity<PetDTO> addPet(@RequestBody Pet pet, Authentication authentication) {
        String username = authentication.getName();
        PetDTO newPet = petService.addPet(pet, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPet);
    }

    @GetMapping
    public ResponseEntity<List<PetDTO>> getPets(Authentication authentication) {
        String username = authentication.getName();
        List<PetDTO> pets = petService.getPetsByUser(username);
        return ResponseEntity.ok(pets);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        petService.deletePet(id, username);
        return ResponseEntity.noContent().build();
    }
}
