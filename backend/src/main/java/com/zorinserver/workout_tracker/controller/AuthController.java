package com.zorinserver.workout_tracker.controller;

import com.zorinserver.workout_tracker.entity.User;
import com.zorinserver.workout_tracker.repository.UserRepository;
import com.zorinserver.workout_tracker.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent() && passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword())) {
            String token = jwtUtils.generateToken(user.getUsername());
            User logedUser = existingUser.get();

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user_id", logedUser.getId());
            response.put("username", logedUser.getUsername());
            response.put("splits", logedUser.getSplits());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body("Invalid username or password.");
    }
}
