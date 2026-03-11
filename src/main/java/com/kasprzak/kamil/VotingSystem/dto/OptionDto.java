package com.kasprzak.kamil.VotingSystem.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record OptionDto(
        @NotNull Long id,
        @NotEmpty String name
) {}
