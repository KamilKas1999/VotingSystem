package com.kasprzak.kamil.VotingSystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record ElectionDto(
        @NotNull Long id,
        @NotNull String name,
        @NotNull List<OptionDto> options
) {}
