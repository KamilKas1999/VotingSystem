package com.kasprzak.kamil.VotingSystem.services.impl;

import com.kasprzak.kamil.VotingSystem.dto.CreateElectionDto;
import com.kasprzak.kamil.VotingSystem.dto.ElectionDto;
import com.kasprzak.kamil.VotingSystem.dto.OptionDto;
import com.kasprzak.kamil.VotingSystem.entities.Election;
import com.kasprzak.kamil.VotingSystem.entities.VoteOption;
import com.kasprzak.kamil.VotingSystem.repositories.ElectionRepository;
import com.kasprzak.kamil.VotingSystem.services.ElectionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ElectionServiceImpl implements ElectionService {

    private final ElectionRepository electionRepository;

    @Override
    public ElectionDto createElection(CreateElectionDto dto) {

        final var election = Election
                .builder()
                .name(dto.name())
                .build();

        List<VoteOption> candidates = dto.options()
                .stream()
                .map(name -> VoteOption
                        .builder()
                        .name(name)
                        .election(election)
                        .build())
                .toList();

        election.setOptions(candidates);

        var savedElection = electionRepository.save(election);

        List<OptionDto> optionsDto = election.getOptions()
                .stream()
                .map(this::buildOptionDto)
                .toList();

        return buildElectionDto(savedElection, optionsDto);
    }

    @Override
    @Transactional
    public List<ElectionDto> getAllElections() {
        return electionRepository.findAll()
                .stream()
                .map(e -> buildElectionDto(e, e.getOptions()
                        .stream()
                        .map(this::buildOptionDto)
                        .toList()))
                .toList();
    }

    private ElectionDto buildElectionDto(Election savedElection, List<OptionDto> optionsDto) {
        return ElectionDto
                .builder()
                .id(savedElection.getId())
                .name(savedElection.getName())
                .options(optionsDto)
                .build();
    }

    private OptionDto buildOptionDto(VoteOption c) {
        return OptionDto.builder()
                .id(c.getId())
                .name(c.getName())
                .build();
    }
}
