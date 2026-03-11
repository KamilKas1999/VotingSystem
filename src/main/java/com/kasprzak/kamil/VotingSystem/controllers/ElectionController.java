package com.kasprzak.kamil.VotingSystem.controllers;

import com.kasprzak.kamil.VotingSystem.dto.CreateElectionDto;
import com.kasprzak.kamil.VotingSystem.dto.ElectionDto;
import com.kasprzak.kamil.VotingSystem.services.ElectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/elections")
@RequiredArgsConstructor
public class ElectionController {

    private final ElectionService electionService;

    @PostMapping
    public ElectionDto createElection(@RequestBody @Valid CreateElectionDto dto) {
        return electionService.createElection(dto);
    }

    @GetMapping
    public List<ElectionDto> getAllElections() {
        return electionService.getAllElections();
    }
}
