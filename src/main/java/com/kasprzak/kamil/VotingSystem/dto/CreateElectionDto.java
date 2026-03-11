package com.kasprzak.kamil.VotingSystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.List;

@Builder
public record CreateElectionDto(
        @NotBlank String name,
        @NotEmpty List<String> options
) {}
