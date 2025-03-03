package com.example.project.service;

import com.example.project.dto.PetDTO;
import com.example.project.dto.UserDTO;
import com.example.project.entities.User;
import com.example.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public UserDTO getUserProfile(String email) {
        User user = findByEmail(email); // Kullanıcıyı getir
        List<PetDTO> petDTOs = user.getPets().stream()
                .map(PetDTO::new) // Her bir Pet nesnesini PetDTO'ya dönüştür
                .collect(Collectors.toList());
        return new UserDTO(user, petDTOs);
    }

}

