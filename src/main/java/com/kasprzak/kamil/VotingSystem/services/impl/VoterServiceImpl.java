package com.kasprzak.kamil.VotingSystem.services.impl;

import com.kasprzak.kamil.VotingSystem.dto.CreateVoterDto;
import com.kasprzak.kamil.VotingSystem.dto.VoterDto;
import com.kasprzak.kamil.VotingSystem.entities.Voter;
import com.kasprzak.kamil.VotingSystem.exceptions.VoterNotFoundException;
import com.kasprzak.kamil.VotingSystem.repositories.VoterRepository;
import com.kasprzak.kamil.VotingSystem.services.VoterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoterServiceImpl implements VoterService {

    private final VoterRepository voterRepository;

    @Override
    public VoterDto createVoter(CreateVoterDto dto) {

        var voter = Voter
                .builder()
                .name(dto.name())
                .email(dto.email())
                .blocked(false)
                .build();

        voter = voterRepository.save(voter);

        return buildVoterDto(voter);
    }

    @Override
    public void blockVoter(Long id) {
        updateIsBlocked(id, true);
    }

    @Override
    public void unblockVoter(Long id) {
        updateIsBlocked(id, false);
    }

    @Override
    public Page<VoterDto> getAllVoters(Pageable pageable) {
        return voterRepository.findAll(pageable)
                .map(this::buildVoterDto);
    }

    private VoterDto buildVoterDto(Voter e) {
        return VoterDto
                .builder()
                .id(e.getId())
                .name(e.getName())
                .email(e.getEmail())
                .blocked(e.isBlocked())
                .build();
    }

    @Transactional
    private void updateIsBlocked(Long id, boolean blocked) {
        Voter voter = voterRepository.findById(id)
                .orElseThrow(() -> new VoterNotFoundException("Voter not found"));

        voter.setBlocked(blocked);
    }
}
