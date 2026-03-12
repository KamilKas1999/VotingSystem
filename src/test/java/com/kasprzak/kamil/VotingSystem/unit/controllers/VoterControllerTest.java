package com.kasprzak.kamil.VotingSystem.unit.controllers;

import com.kasprzak.kamil.VotingSystem.controllers.VoterController;
import com.kasprzak.kamil.VotingSystem.dto.CreateVoterDto;
import com.kasprzak.kamil.VotingSystem.dto.VoterDto;
import com.kasprzak.kamil.VotingSystem.services.VoterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoterControllerTest {

    @Mock
    private VoterService voterService;

    @InjectMocks
    private VoterController voterController;

    @Test
    void shouldReturnVoterDto() {
        // given
        var dto = new CreateVoterDto("name", "email");
        var expected = new VoterDto(1L, "name", "email", false);

        when(voterService.createVoter(dto)).thenReturn(expected);

        //when
        VoterDto actual = voterController.createVoter(dto);

        // then
        assertEquals(expected.id(), actual.id());
        assertEquals(expected.name(), actual.name());
        assertEquals(expected.email(), actual.email());
        assertEquals(expected.blocked(), actual.blocked());
        verify(voterService, times(1)).createVoter(dto);
    }

    @Test
    void shouldBlockUser() {
        // given
        Long voterId = 1L;

        // when
        voterController.blockVoter(voterId);

        // then
        verify(voterService, times(1)).blockVoter(voterId);
    }

    @Test
    void shouldUnblockVoter() {
        // given
        Long voterId = 1L;

        // when
        voterController.unblockVoter(voterId);

        // then
        verify(voterService, times(1)).unblockVoter(voterId);
    }

    @Test
    void shouldReturnList() {
        // given
        var expectedList = List.of(
                new VoterDto(1L, "name", "email", false),
                new VoterDto(2L, "name", "email", true)
        );

        Pageable pageable = PageRequest.of(0, 2);

        Page<VoterDto> expectedPage = new PageImpl<>(expectedList, pageable, expectedList.size());

        when(voterService.getAllVoters(any())).thenReturn(expectedPage);

        // when
        Page<VoterDto> actualList = voterController.getAllVoters(0,2);

        // then
        assertEquals(2, actualList.getSize());
        assertEquals(expectedList.getFirst().name(), actualList.getContent().getFirst().name());
        assertEquals(expectedList.get(1).name(), actualList.getContent().getLast().name());
        verify(voterService, times(1)).getAllVoters(any());
    }
}