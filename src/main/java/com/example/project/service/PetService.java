package com.example.project.service;

import com.example.project.dto.PetDTO;
import com.example.project.entities.Pet;
import com.example.project.entities.User;
import com.example.project.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserService userService;

    public PetDTO addPet(Pet pet, String email) {
        User owner = userService.findByEmail(email);
        pet.setOwner(owner);
        Pet savedPet = petRepository.save(pet);
        return new PetDTO(savedPet);
    }

    public List<PetDTO> getPetsByUser(String email) {
        User owner = userService.findByEmail(email);
        return petRepository.findByOwnerId(owner.getId())
                .stream()
                .map(PetDTO::new)
                .collect(Collectors.toList());
    }

    public void deletePet(Long id, String username) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evcil hayvan bulunamadı"));
        if (!pet.getOwner().getUsername().equals(username)) {
            throw new RuntimeException("Bu evcil hayvanı silme yetkiniz yok");
        }
        petRepository.delete(pet);
    }

}
