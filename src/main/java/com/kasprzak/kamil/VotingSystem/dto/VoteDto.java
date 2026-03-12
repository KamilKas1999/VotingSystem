package com.kasprzak.kamil.VotingSystem.dto;

import lombok.Builder;

@Builder
public record VoteDto(
        String voterName,
        String electionName,
        String optionName
) {
}
