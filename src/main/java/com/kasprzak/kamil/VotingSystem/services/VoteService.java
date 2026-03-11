package com.kasprzak.kamil.VotingSystem.services;

import com.kasprzak.kamil.VotingSystem.dto.VoteRequestDto;

public interface VoteService {

    void vote(VoteRequestDto dto);
}
