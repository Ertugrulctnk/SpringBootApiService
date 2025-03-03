package com.example.project.service;

import com.example.project.entities.Admin;
import com.example.project.entities.User;

import com.example.project.repository.AdminRepository;
import com.example.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Kullanıcıyı User tablosundan e-posta ile arıyoruz
        User user = userRepository.findByEmail(email)
                .orElse(null); // Eğer User bulunmazsa null dönebiliriz

        // Eğer User bulunmazsa, Admin tablosunu kontrol ediyoruz
        if (user == null) {
            Admin admin = adminRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Admin not found with email: " + email));

            // Admin kullanıcısını UserDetails formatında döndürüyoruz
            return org.springframework.security.core.userdetails.User.builder()
                    .username(admin.getEmail())
                    .roles("ADMIN")  // Admin'in rolü olarak ADMIN belirliyoruz
                    .build();
        }

        // Eğer User bulunursa, User tablosundan gelen verileri kullanıyoruz
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password("{noop}")
                .roles(user.getRole().name())  // User'ın rolünü alıyoruz
                .build();
    }
}
