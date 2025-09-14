package com.juantapia.CleanArchitectureService.application.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserCommand {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}