package com.kasprzak.kamil.VotingSystem.unit.controllers;

import com.kasprzak.kamil.VotingSystem.controllers.ElectionController;
import com.kasprzak.kamil.VotingSystem.dto.CreateElectionDto;
import com.kasprzak.kamil.VotingSystem.dto.ElectionDto;
import com.kasprzak.kamil.VotingSystem.services.ElectionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ElectionControllerTest {

    @Mock
    private ElectionService electionService;

    @InjectMocks
    private ElectionController electionController;


    @Test
    void shouldReturnElectionDto() {
        // given
        var dto = CreateElectionDto.builder().name("name").build();
        var expected = ElectionDto.builder().id(1L).name("name").build();

        when(electionService.createElection(dto)).thenReturn(expected);

        // when
        ElectionDto actual = electionController.createElection(dto);

        // then
        assertEquals(expected.id(), actual.id());
        assertEquals(expected.name(), actual.name());
        verify(electionService, times(1)).createElection(dto);
    }

    @Test
    void shouldReturnListOfElections() {
        //given
        List<ElectionDto> expectedList = List.of(
                ElectionDto.builder().id(1L).name("name").build(),
                ElectionDto.builder().id(1L).name("name").build()
        );

        when(electionService.getAllElections()).thenReturn(expectedList);

        // when
        List<ElectionDto> actualList = electionController.getAllElections();

        // then
        assertEquals(2, actualList.size());
        assertEquals("name", actualList.get(0).name());
        assertEquals("name", actualList.get(1).name());
        verify(electionService, times(1)).getAllElections();
    }
}