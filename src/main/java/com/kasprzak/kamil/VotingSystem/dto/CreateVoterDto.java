package com.kasprzak.kamil.VotingSystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateVoterDto(
        @NotBlank String name,
        @NotBlank @Email String email
) {}
