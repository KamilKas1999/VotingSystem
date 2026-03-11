package com.kasprzak.kamil.VotingSystem.services;

import com.kasprzak.kamil.VotingSystem.dto.CreateVoterDto;
import com.kasprzak.kamil.VotingSystem.dto.VoterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VoterService {
    VoterDto createVoter(CreateVoterDto dto);

    void blockVoter(Long id);

    void unblockVoter(Long id);

    Page<VoterDto> getAllVoters(Pageable pageable);
}
