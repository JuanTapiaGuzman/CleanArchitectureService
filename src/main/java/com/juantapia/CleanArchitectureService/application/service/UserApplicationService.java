package com.juantapia.CleanArchitectureService.application.service;

import com.juantapia.CleanArchitectureService.application.dto.UserResponse;
import com.juantapia.CleanArchitectureService.application.command.CreateUserCommand;
import com.juantapia.CleanArchitectureService.application.command.UpdateUserCommand;
import com.juantapia.CleanArchitectureService.application.command.DeleteUserCommand;
import com.juantapia.CleanArchitectureService.application.query.GetUserByIdQuery;
import com.juantapia.CleanArchitectureService.application.query.GetAllUsersQuery;
import com.juantapia.CleanArchitectureService.domain.model.User;
import com.juantapia.CleanArchitectureService.domain.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserApplicationService {

    private final UserRepository userRepository;

    public UserResponse handle(CreateUserCommand command) {
        User user = User.builder()
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .email(command.getEmail())
                .phoneNumber(command.getPhoneNumber())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    public Optional<UserResponse> handle(GetUserByIdQuery query) {
        return userRepository.findById(query.getId())
                .map(this::mapToResponse);
    }

    public List<UserResponse> handle(GetAllUsersQuery query) {
        return userRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public UserResponse handle(UpdateUserCommand command) {
        User existingUser = userRepository.findById(command.getId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + command.getId()));

        User updatedUser = User.builder()
                .id(existingUser.getId())
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .email(command.getEmail())
                .phoneNumber(command.getPhoneNumber())
                .createdAt(existingUser.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(updatedUser);
        return mapToResponse(savedUser);
    }

    public void handle(DeleteUserCommand command) {
        if (!userRepository.existsById(command.getId())) {
            throw new RuntimeException("User not found with id: " + command.getId());
        }
        userRepository.deleteById(command.getId());
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}