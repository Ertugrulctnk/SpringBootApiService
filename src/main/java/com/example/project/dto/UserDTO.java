package com.example.project.dto;

import com.example.project.entities.User;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String phone;
    private String email;
    private String location;
    private List<PetDTO> pets;

    public UserDTO(User user, List<PetDTO> petDTOs) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.location = user.getLocation();
        this.pets = petDTOs;
    }
}
