package com.kasprzak.kamil.VotingSystem.dto;

import lombok.Builder;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotNull;

@Builder
public record VoteRequestDto(
        @NotNull(message = "Voter ID must not be null")
        @Positive(message = "Voter ID must be a positive number")
        Long voterId,
        @NotNull(message = "Election ID must not be null")
        @Positive(message = "Election ID must be a positive number")
        Long electionId,
        @NotNull(message = "Option ID must not be null")
        @Positive(message = "Option ID must be a positive number")
        Long optionId
) {}
