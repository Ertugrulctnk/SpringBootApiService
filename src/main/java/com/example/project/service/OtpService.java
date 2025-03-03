package com.example.project.service;

import com.example.project.entities.User;
import com.example.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {

    @Autowired
    private UserRepository userRepository;

    private Map<String, String> otpStorage = new ConcurrentHashMap<>();
    private Map<String, Long> otpTimestamp = new ConcurrentHashMap<>();

    public boolean generateAndSendOtp(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String otp = String.format("%06d", new Random().nextInt(1000000));
        otpStorage.put(email, otp);
        otpTimestamp.put(email, System.currentTimeMillis());
        // Simulate sending OTP via email
        System.out.println("OTP for " + email + " is: " + otp);
        return true;
    }

    public boolean validateOtp(String email, String otp) {
        String storedOtp = otpStorage.get(email);
        Long sentTime = otpTimestamp.get(email);

        if (storedOtp == null || sentTime == null) {
            return false;
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime - sentTime > 60000) { // OTP valid for 1 minute
            otpStorage.remove(email);
            otpTimestamp.remove(email);
            return false;
        }

        boolean isValid = storedOtp.equals(otp);
        if (isValid) {
            otpStorage.remove(email);
            otpTimestamp.remove(email);
        }
        return isValid;
    }
}