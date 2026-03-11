package com.kasprzak.kamil.VotingSystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.List;

@Builder
public record CreateElectionDto(
        @NotBlank(message = "Voting name cannot be empty") String name,
        @NotEmpty(message = "Voting options cannot be empty") List<String> options
) {}
