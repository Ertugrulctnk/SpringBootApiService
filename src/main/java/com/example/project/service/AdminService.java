package com.example.project.service;

import com.example.project.entities.Admin;
import com.example.project.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    @Autowired
    private final AdminRepository adminRepository;

    public Admin addAdmin(Admin admin) {
       return adminRepository.save(admin);
    }
}