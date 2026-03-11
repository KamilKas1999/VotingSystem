package com.kasprzak.kamil.VotingSystem.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record VoterDto(
        @NotNull Long id,
        @NotEmpty String name,
        @NotEmpty String email,
        @NotNull boolean blocked
) {}
