package com.example.project.dto;

import com.example.project.entities.Pet;
import lombok.Data;

@Data
public class PetDTO {
    private Long id;
    private String species;
    private String breed;
    private int age;
    private String gender;
    private String personality;
    private Long ownerId;

    public PetDTO(Pet pet) {
        this.id = pet.getId();
        this.species = pet.getSpecies();
        this.breed = pet.getBreed();
        this.age = pet.getAge();
        this.gender = pet.getGender();
        this.personality = pet.getPersonality();
        this.ownerId = pet.getOwner().getId();
    }
}
