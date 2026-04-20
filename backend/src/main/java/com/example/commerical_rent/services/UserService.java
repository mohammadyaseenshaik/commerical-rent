package com.example.commerical_rent.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.commerical_rent.dtos.UserDTO;
import com.example.commerical_rent.entity.User;
import com.example.commerical_rent.enums.Role;
import com.example.commerical_rent.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(UserDTO userDTO) {
        // Validate email is unique
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        // Validate phone number is unique
        if (userRepository.findByPhoneNumber(userDTO.getPhoneNumber()).isPresent()) {
            throw new IllegalArgumentException("Phone number already exists");
        }
        
        // Create new User entity from DTO
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setRole(userDTO.getRole());
        user.setCreatedAt(LocalDateTime.now());
        
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}
