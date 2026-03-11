package com.kasprzak.kamil.VotingSystem.dto;

import lombok.Builder;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotNull;

@Builder
public record VoteRequestDto(
        @NotNull @Positive Long voterId,
        @NotNull @Positive Long electionId,
        @NotNull @Positive Long optionId
) {}
