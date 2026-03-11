package com.kasprzak.kamil.VotingSystem.services.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.kasprzak.kamil.VotingSystem.dto.VoteRequestDto;
import com.kasprzak.kamil.VotingSystem.entities.Election;
import com.kasprzak.kamil.VotingSystem.entities.VoteOption;
import com.kasprzak.kamil.VotingSystem.entities.Voter;
import com.kasprzak.kamil.VotingSystem.exceptions.*;
import com.kasprzak.kamil.VotingSystem.repositories.ElectionRepository;
import com.kasprzak.kamil.VotingSystem.repositories.VoteOptionRepository;
import com.kasprzak.kamil.VotingSystem.repositories.VoteRepository;
import com.kasprzak.kamil.VotingSystem.repositories.VoterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoteServiceImplTest {

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private VoterRepository voterRepository;

    @Mock
    private ElectionRepository electionRepository;

    @Mock
    private VoteOptionRepository voteOptionRepository;

    @InjectMocks
    private VoteServiceImpl voteService;

    @Test
    void shouldVoteSuccessfully() {

        // given
        var voter = Voter.builder().id(1L).blocked(false).build();
        var election = Election.builder().id(1L).build();
        var option = VoteOption.builder().id(1L).election(election).build();
        var dto = VoteRequestDto.builder().electionId(1L).voterId(1L).optionId(1L).build();

        when(voterRepository.findById(1L)).thenReturn(Optional.of(voter));
        when(electionRepository.findById(1L)).thenReturn(Optional.of(election));
        when(voteOptionRepository.findById(1L)).thenReturn(Optional.of(option));
        when(voteRepository.existsByVoterIdAndElectionId(1L, 1L)).thenReturn(false);

        // when
        voteService.vote(dto);

        // then
        verify(voteRepository, times(1)).save(any());
    }

    @Test
    void shouldThrowExceptionIfVoterBlocked() {

        // given
        var voter = Voter.builder().id(1L).blocked(true).build();
        var dto = VoteRequestDto.builder().electionId(1L).voterId(1L).optionId(1L).build();

        when(voterRepository.findById(1L)).thenReturn(Optional.of(voter));

        // when + then
        var ex = assertThrows(VoterIsBlockedException.class,
                () -> voteService.vote(dto));
        assertEquals("Voter is blocked", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionIfVoterNotFound() {

        // given
        var dto = VoteRequestDto.builder().electionId(1L).voterId(1L).optionId(1L).build();
        when(voterRepository.findById(1L)).thenReturn(Optional.empty());

        // when + then
        var ex = assertThrows(VoterNotFoundException.class,
                () -> voteService.vote(dto));
        assertEquals("Voter not found", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionIfElectionNotFound() {

        // given
        var voter = Voter.builder().id(1L).blocked(false).build();
        var dto = VoteRequestDto.builder().electionId(1L).voterId(1L).optionId(1L).build();

        when(voterRepository.findById(1L)).thenReturn(Optional.of(voter));
        when(electionRepository.findById(1L)).thenReturn(Optional.empty());

        // when + then
        var ex = assertThrows(ElectionNotFoundException.class,
                () -> voteService.vote(dto));
        assertEquals("Election not found", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionIfOptionNotFound() {

        // given
        var voter = Voter.builder().id(1L).blocked(false).build();
        var election = Election.builder().id(1L).build();
        var dto = VoteRequestDto.builder().electionId(1L).voterId(1L).optionId(1L).build();

        when(voterRepository.findById(1L)).thenReturn(Optional.of(voter));
        when(electionRepository.findById(1L)).thenReturn(Optional.of(election));
        when(voteOptionRepository.findById(1L)).thenReturn(Optional.empty());

        // when + then
        var ex = assertThrows(VoteOptionNotFoundException.class,
                () -> voteService.vote(dto));
        assertEquals("Option not found", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionIfOptionDoesNotBelongToElection() {

        // given
        var voter = Voter.builder().id(1L).blocked(false).build();
        var election = Election.builder().id(1L).build();
        var otherElection = Election.builder().id(2L).build();
        var option = VoteOption.builder().id(1L).election(otherElection).build();
        var dto = VoteRequestDto.builder().electionId(1L).voterId(1L).optionId(1L).build();

        when(voterRepository.findById(1L)).thenReturn(Optional.of(voter));
        when(electionRepository.findById(1L)).thenReturn(Optional.of(election));
        when(voteOptionRepository.findById(1L)).thenReturn(Optional.of(option));

        // when + then
        var ex = assertThrows(OptionNotBelongsToElectionException.class,
                () -> voteService.vote(dto));
        assertEquals("Candidate does not belong to this election", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionIfUserAlreadyVoted() {

        // given
        var voter = Voter.builder().id(1L).blocked(false).build();
        var election = Election.builder().id(1L).build();
        var option = VoteOption.builder().id(1L).election(election).build();
        var dto = VoteRequestDto.builder().electionId(1L).voterId(1L).optionId(1L).build();

        when(voterRepository.findById(1L)).thenReturn(Optional.of(voter));
        when(electionRepository.findById(1L)).thenReturn(Optional.of(election));
        when(voteOptionRepository.findById(1L)).thenReturn(Optional.of(option));
        when(voteRepository.existsByVoterIdAndElectionId(1L, 1L)).thenReturn(true);

        // when + then
        var ex = assertThrows(UserAlreadyVotedException.class,
                () -> voteService.vote(dto));
        assertEquals("Voter already voted in this election", ex.getMessage());
    }
}