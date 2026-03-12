package com.kasprzak.kamil.VotingSystem.unit.services;

import com.kasprzak.kamil.VotingSystem.dto.CreateVoterDto;
import com.kasprzak.kamil.VotingSystem.dto.VoterDto;
import com.kasprzak.kamil.VotingSystem.entities.Voter;
import com.kasprzak.kamil.VotingSystem.exceptions.VoterNotFoundException;
import com.kasprzak.kamil.VotingSystem.repositories.VoterRepository;
import com.kasprzak.kamil.VotingSystem.services.impl.VoterServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
        var expectedList = List.of(
                new Voter(1L, "name", "email", false),
                new Voter(2L, "name", "email", true)
        );

        Pageable pageable = PageRequest.of(0, 2);

        Page<Voter> expectedPage = new PageImpl<>(expectedList, pageable, expectedList.size());

        when(voterRepository.findAll(any(Pageable.class))).thenReturn(expectedPage);

        // when
        Page<VoterDto> result = voterService.getAllVoters(pageable);

        // then
        assertEquals(2, result.getSize());
        assertEquals("name", result.getContent().getFirst().name());
        assertEquals("email", result.getContent().getFirst().email());
        assertFalse(result.getContent().getFirst().blocked());
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