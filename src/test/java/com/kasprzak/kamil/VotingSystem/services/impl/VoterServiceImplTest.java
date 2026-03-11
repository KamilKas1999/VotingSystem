package com.kasprzak.kamil.VotingSystem.services.impl;

import com.kasprzak.kamil.VotingSystem.dto.CreateVoterDto;
import com.kasprzak.kamil.VotingSystem.dto.VoterDto;
import com.kasprzak.kamil.VotingSystem.entities.Voter;
import com.kasprzak.kamil.VotingSystem.exceptions.VoterNotFoundException;
import com.kasprzak.kamil.VotingSystem.repositories.VoterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VoterServiceImplTest {

    @Mock
    private VoterRepository voterRepository;

    @InjectMocks
    private VoterServiceImpl voterService;

    @Test
    void shouldCreateVoter() {

        // given
        var dto = new CreateVoterDto("name", "email");

        var saved = Voter.builder()
                .id(1L)
                .name("name")
                .email("email")
                .blocked(false)
                .build();

        when(voterRepository.save(org.mockito.ArgumentMatchers.any(Voter.class)))
                .thenReturn(saved);

        // when
        VoterDto result = voterService.createVoter(dto);

        // then
        assertEquals(1L, result.id());
        assertEquals(dto.name(), result.name());
        assertEquals(dto.email(), result.email());
        assertFalse(result.blocked());
    }

    @Test
    void shouldBlockVoter() {

        // given
        var voter = Voter.builder()
                .id(1L)
                .name("name")
                .email("email")
                .blocked(false)
                .build();

        when(voterRepository.findById(1L))
                .thenReturn(Optional.of(voter));

        // when
        voterService.blockVoter(1L);

        // then
        assertTrue(voter.isBlocked());
    }

    @Test
    void shouldUnblockVoter() {

        // given
        var voter = Voter.builder()
                .id(1L)
                .name("name")
                .email("email")
                .blocked(true)
                .build();

        when(voterRepository.findById(1L))
                .thenReturn(Optional.of(voter));

        // when
        voterService.unblockVoter(1L);

        // then
        assertFalse(voter.isBlocked());
    }

    @Test
    void shouldReturnAllVoters() {

        // given
        var voter = Voter.builder()
                .id(1L)
                .name("name")
                .email("email")
                .blocked(false)
                .build();

        when(voterRepository.findAll())
                .thenReturn(List.of(voter));

        // when
        List<VoterDto> result = voterService.getAllVoters();

        // then
        assertEquals(1, result.size());
        assertEquals("name", result.getFirst().name());
        assertEquals("email", result.getFirst().email());
        assertFalse(result.getFirst().blocked());
    }

    @Test
    void shouldThrowExceptionWhenVoterNotFound() {

        // given
        when(voterRepository.findById(1L))
                .thenReturn(Optional.empty());

        // when + then
        assertThrows(
                VoterNotFoundException.class,
                () -> voterService.blockVoter(1L)
        );
    }
}