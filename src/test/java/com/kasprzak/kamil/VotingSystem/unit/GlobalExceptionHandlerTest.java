package com.kasprzak.kamil.VotingSystem.unit;

import com.kasprzak.kamil.VotingSystem.GlobalExceptionHandler;
import com.kasprzak.kamil.VotingSystem.dto.ErrorResponse;
import com.kasprzak.kamil.VotingSystem.exceptions.*;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void shouldHandleElectionNotFound() {

        // given
        var ex = new ElectionNotFoundException("Election not found");

        // when
        ResponseEntity<ErrorResponse> response = handler.handleElectionNotFound(ex);

        // then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getBody().status());
        assertEquals("Election not found", response.getBody().message());
        assertEquals("Not Found", response.getBody().error());
        assertNotNull(response.getBody().timestamp());
    }

    @Test
    void shouldHandleVoteOptionNotFound() {

        // given
        var ex = new VoteOptionNotFoundException("Option not found");

        // when
        ResponseEntity<ErrorResponse> response = handler.handleVoteOptionNotFound(ex);

        // then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Option not found", response.getBody().message());
    }

    @Test
    void shouldHandleVoterNotFound() {

        // given
        var ex = new VoterNotFoundException("Voter not found");

        // when
        ResponseEntity<ErrorResponse> response = handler.handleVoterNotFound(ex);

        // then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Voter not found", response.getBody().message());
    }

    @Test
    void shouldHandleVoterIsBlocked() {

        // given
        var ex = new VoterIsBlockedException("Voter is blocked");

        // when
        ResponseEntity<ErrorResponse> response = handler.handleVoterBlocked(ex);

        // then
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Voter is blocked", response.getBody().message());
    }

    @Test
    void shouldHandleUserAlreadyVoted() {

        // given
        var ex = new UserAlreadyVotedException("Voter already voted");

        // when
        ResponseEntity<ErrorResponse> response = handler.handleUserAlreadyVoted(ex);

        // then
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Voter already voted", response.getBody().message());
    }

    @Test
    void shouldHandleOptionNotBelongsToElection() {

        // given
        var ex = new OptionNotBelongsToElectionException("Candidate does not belong to this election");

        // when
        ResponseEntity<ErrorResponse> response = handler.handleOptionNotBelongs(ex);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Candidate does not belong to this election", response.getBody().message());
    }
}