package com.example.project.controller;

import com.example.project.dto.OtpRequest;
import com.example.project.dto.UserDTO;
import com.example.project.entities.User;
import com.example.project.jwtConfigurations.JwtUtil;
import com.example.project.service.OtpService;
import com.example.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private OtpService otpService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User newUser = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestBody OtpRequest otpRequest) {
        boolean isSent = otpService.generateAndSendOtp(otpRequest.getEmail());
        if (isSent) {
            return ResponseEntity.ok("OTP sent successfully to " + otpRequest.getEmail());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to send OTP");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginWithOtp(@RequestBody OtpRequest otpRequest) {
        boolean isValid = otpService.validateOtp(otpRequest.getEmail(), otpRequest.getOtp());
        if (isValid) {
            String token = jwtUtil.generateToken(otpRequest.getEmail());
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP or expired");
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
//            );
//            // Doğrulama başarılı ise token üret ve döndür
//            String token = jwtUtil.generateToken(authRequest.getUsername());
//            return ResponseEntity.ok(token);
//
//        } catch (Exception e) {
//            // Doğrulama başarısız ise hata mesajı döndür
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Kullanıcı adı veya şifre hatalı!");
//
//        }
 //   }

    // Kendi profil bilgilerini alma
    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile(Authentication authentication) {
        String email = authentication.getName(); // Oturum açan kullanıcının adını al
        UserDTO user = userService.getUserProfile(email);
        return ResponseEntity.ok(user);
    }
}
