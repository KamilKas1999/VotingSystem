package com.kasprzak.kamil.VotingSystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record CreateVoterDto(
        @NotBlank(message = "Voter name cannot be empty") String name,
        @NotBlank @Email String email
) {}
