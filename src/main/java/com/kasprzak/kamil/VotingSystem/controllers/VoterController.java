package com.kasprzak.kamil.VotingSystem.controllers;

import com.kasprzak.kamil.VotingSystem.dto.CreateVoterDto;
import com.kasprzak.kamil.VotingSystem.dto.VoterDto;
import com.kasprzak.kamil.VotingSystem.services.VoterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/voters")
@RequiredArgsConstructor
public class VoterController {

    private final VoterService voterService;

    @PostMapping
    public VoterDto createVoter(@RequestBody @Valid CreateVoterDto dto) {
        return voterService.createVoter(dto);
    }

    @PostMapping("/{id}/block")
    public void blockVoter(@PathVariable Long id) {
        voterService.blockVoter(id);
    }

    @PostMapping("/{id}/unblock")
    public void unblockVoter(@PathVariable Long id) {
        voterService.unblockVoter(id);
    }

    @GetMapping
    public List<VoterDto> getAllVoters() {
        return voterService.getAllVoters();
    }
}
