package com.kasprzak.kamil.VotingSystem.services;

import com.kasprzak.kamil.VotingSystem.dto.VoteDto;
import com.kasprzak.kamil.VotingSystem.dto.VoteRequestDto;
import com.kasprzak.kamil.VotingSystem.dto.VoterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VoteService {

    void vote(VoteRequestDto dto);

    Page<VoteDto> getAllVotes(Pageable pageable);
}
