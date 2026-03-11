package com.kasprzak.kamil.VotingSystem.services;

import com.kasprzak.kamil.VotingSystem.dto.CreateElectionDto;
import com.kasprzak.kamil.VotingSystem.dto.ElectionDto;

import java.util.List;

public interface ElectionService {

    ElectionDto createElection(CreateElectionDto dto);

    List<ElectionDto> getAllElections();
}
