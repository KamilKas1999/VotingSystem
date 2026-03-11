package com.kasprzak.kamil.VotingSystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record ElectionDto(
        Long id,
        String name,
        List<OptionDto> options
) {}
