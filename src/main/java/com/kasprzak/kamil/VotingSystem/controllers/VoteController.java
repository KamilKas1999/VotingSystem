package com.kasprzak.kamil.VotingSystem.controllers;

import com.kasprzak.kamil.VotingSystem.dto.VoteRequestDto;
import com.kasprzak.kamil.VotingSystem.services.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/votes")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public void vote(@RequestBody @Valid VoteRequestDto dto) {
        voteService.vote(dto);
    }
}
