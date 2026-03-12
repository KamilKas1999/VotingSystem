package com.kasprzak.kamil.VotingSystem.unit.controllers;

import com.kasprzak.kamil.VotingSystem.controllers.VoteController;
import com.kasprzak.kamil.VotingSystem.dto.VoteRequestDto;
import com.kasprzak.kamil.VotingSystem.services.VoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VoteControllerTest {

    @Mock
    private VoteService voteService;

    @InjectMocks
    private VoteController voteController;

    @Test
    void shouldCallVoteService() {
        //given
        VoteRequestDto dto = VoteRequestDto.builder()
                .electionId(1L)
                .voterId(2L)
                .optionId(3L)
                .build();

        // when
        voteController.vote(dto);

        // then
        verify(voteService, times(1)).vote(dto);
    }
}