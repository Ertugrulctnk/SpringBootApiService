package com.example.project.controller;

import com.example.project.entities.Admin;
import com.example.project.entities.User;
import com.example.project.repository.UserRepository;
import com.example.project.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final AdminService  adminService;

   @PostMapping("/register")
    public ResponseEntity<Admin> registerAdmin(@RequestBody Admin admin) {
       Admin newAdmin = adminService.addAdmin(admin);
       return ResponseEntity.status(HttpStatus.CREATED).body(newAdmin);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }
}
