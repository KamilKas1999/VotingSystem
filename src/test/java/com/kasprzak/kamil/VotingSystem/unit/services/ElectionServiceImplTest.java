package com.kasprzak.kamil.VotingSystem.unit.services;

import com.kasprzak.kamil.VotingSystem.dto.CreateElectionDto;
import com.kasprzak.kamil.VotingSystem.dto.ElectionDto;
import com.kasprzak.kamil.VotingSystem.entities.Election;
import com.kasprzak.kamil.VotingSystem.entities.VoteOption;
import com.kasprzak.kamil.VotingSystem.repositories.ElectionRepository;
import com.kasprzak.kamil.VotingSystem.services.impl.ElectionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ElectionServiceImplTest {

    @Mock
    private ElectionRepository electionRepository;

    @InjectMocks
    private ElectionServiceImpl electionService;

    private CreateElectionDto createElectionDto;

    private Election election;

    @BeforeEach
    void setUp() {
        createElectionDto = new CreateElectionDto(
                "name",
                List.of("option A", "option B")
        );

        election= Election.builder()
                .id(1L)
                .name("name")
                .build();

        var option1 = VoteOption.builder()
                .id(1L)
                .name("option A")
                .election(election)
                .build();

        var option2 = VoteOption.builder()
                .id(2L)
                .name("option B")
                .election(election)
                .build();


        election.setOptions(List.of(option1, option2));
    }

    @Test
    void shouldCreateElection() {

        // given
        when(electionRepository.save(any(Election.class)))
                .thenReturn(election);

        // when
        ElectionDto result = electionService.createElection(createElectionDto);

        // then
        assertEquals(1L, result.id());
        assertEquals(createElectionDto.name(), result.name());
        assertEquals(2, result.options().size());
        assertEquals(createElectionDto.options().getFirst(), result.options().getFirst().name());
        assertEquals(createElectionDto.options().getLast(), result.options().getLast().name());
    }

    @Test
    void shouldReturnAllElections() {

        // given
        when(electionRepository.findAll())
                .thenReturn(List.of(election));

        // when
        List<ElectionDto> result = electionService.getAllElections();

        // then
        assertEquals(1, result.size());
        assertEquals(1L, result.getFirst().id());
        assertEquals(createElectionDto.name(), result.getFirst().name());
        assertEquals(2, result.getFirst().options().size());
        assertEquals(createElectionDto.options().getFirst(), result.getFirst().options().getFirst().name());
    }
}