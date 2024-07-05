package com.example.userservice.service;

import com.example.userservice.entity.Image;
import com.example.userservice.entity.Profile;
import com.example.userservice.entity.User;
import com.example.grpc.*;
import com.example.userservice.repository.ImageRepository;
import com.example.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public User registerUser(RegisterUserRequest request) {
        if (checkUsername(request.getUsername())) {
            throw new RuntimeException("User already exists");
        }

        Image image = new Image();
        image.setPath(request.getProfile().getImage().getPath());
        image.setOriginalFilename(request.getProfile().getImage().getOriginalFilename());
        image.setFileExtension(request.getProfile().getImage().getFileExtension());
        image.setDescription(request.getProfile().getImage().getDescription());
        image = imageRepository.save(image);

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(hashPassword(request.getPassword()));
        user.setEmail(request.getEmail());
        user = userRepository.save(user);

        Profile profile = new Profile();
        profile.setGreeting(request.getProfile().getGreeting());
        profile.setProfileImage(image);
        profile.setUser(user);

        user.setProfile(profile);
        userRepository.save(user);

        return user;
    }

    public String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    private boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return checkPassword(rawPassword, encodedPassword);
    }

    public User findUserByUserId(Long userId) {
        return userRepository.findByIdWithProfileAndImage(userId).orElse(null);
    }

    public boolean checkUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public Map<String, Object> authenticateUser(String username, String rawPassword) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        Map<String, Object> responseData = new HashMap<>();

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            boolean authenticated = validatePassword(rawPassword, user.getPassword());

            responseData.put("authenticated", authenticated);
            responseData.put("userId", user.getId());

            return responseData;
        }

        responseData.put("authenticated", false);
        return responseData;
    }

    @Transactional
    public Optional<String> updatePassword(Long userId, String currentPassword, String newPassword) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (validatePassword(currentPassword, user.getPassword())) {
                user.setPassword(hashPassword(newPassword));
                userRepository.save(user);
                return Optional.of(newPassword);
            } else {
                return Optional.of(currentPassword);
            }
        }
        return Optional.empty();
    }

    @Transactional
    public User updateProfile(UpdateProfileRequest request) {
        Optional<User> userOptional = userRepository.findById(request.getUserId());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Profile profile = user.getProfile();
            profile.setGreeting(request.getProfile().getGreeting());

            Image image = profile.getProfileImage();
            image.setPath(request.getProfile().getImage().getPath());
            image.setOriginalFilename(request.getProfile().getImage().getOriginalFilename());
            image.setFileExtension(request.getProfile().getImage().getFileExtension());
            image.setDescription(request.getProfile().getImage().getDescription());
            imageRepository.save(image);
            userRepository.save(user);
            return user;
        } else {
            throw new RuntimeException("User not found");
        }
    }
}