package com.juantapia.CleanArchitectureService.presentation.controller;

import com.juantapia.CleanArchitectureService.application.command.CreateUserCommand;
import com.juantapia.CleanArchitectureService.application.command.DeleteUserCommand;
import com.juantapia.CleanArchitectureService.application.command.UpdateUserCommand;
import com.juantapia.CleanArchitectureService.application.dto.UserRequest;
import com.juantapia.CleanArchitectureService.application.dto.UserResponse;
import com.juantapia.CleanArchitectureService.application.query.GetAllUsersQuery;
import com.juantapia.CleanArchitectureService.application.query.GetUserByIdQuery;
import com.juantapia.CleanArchitectureService.application.service.UserApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserApplicationService userApplicationService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        CreateUserCommand command = CreateUserCommand.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .build();

        UserResponse response = userApplicationService.handle(command);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        GetUserByIdQuery query = GetUserByIdQuery.builder()
                .id(id)
                .build();

        return userApplicationService.handle(query)
                .map(user -> ResponseEntity.ok(user))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        GetAllUsersQuery query = new GetAllUsersQuery();
        List<UserResponse> users = userApplicationService.handle(query);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        UpdateUserCommand command = UpdateUserCommand.builder()
                .id(id)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .build();

        try {
            UserResponse response = userApplicationService.handle(command);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        DeleteUserCommand command = DeleteUserCommand.builder()
                .id(id)
                .build();

        try {
            userApplicationService.handle(command);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}