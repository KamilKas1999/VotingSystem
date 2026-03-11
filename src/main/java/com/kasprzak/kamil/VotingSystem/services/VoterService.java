package com.kasprzak.kamil.VotingSystem.services;

import com.kasprzak.kamil.VotingSystem.dto.CreateVoterDto;
import com.kasprzak.kamil.VotingSystem.dto.VoterDto;

import java.util.List;

public interface VoterService {
    VoterDto createVoter(CreateVoterDto dto);

    void blockVoter(Long id);

    void unblockVoter(Long id);

    List<VoterDto> getAllVoters();
}
