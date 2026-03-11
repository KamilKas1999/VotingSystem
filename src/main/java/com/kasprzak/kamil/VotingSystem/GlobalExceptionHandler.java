package com.kasprzak.kamil.VotingSystem;

import com.kasprzak.kamil.VotingSystem.dto.ErrorResponse;
import com.kasprzak.kamil.VotingSystem.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.stream.Collectors;

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ElectionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleElectionNotFound (ElectionNotFoundException ex){
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(VoteOptionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleVoteOptionNotFound (VoteOptionNotFoundException ex){
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(VoterNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleVoterNotFound (VoterNotFoundException ex){
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(VoteNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleVoteNotFound (VoteNotFoundException ex){
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(VoterIsBlockedException.class)
    public ResponseEntity<ErrorResponse> handleVoterBlocked (VoterIsBlockedException ex){
        return buildResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyVotedException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyVoted (UserAlreadyVotedException ex){
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(OptionNotBelongsToElectionException.class)
    public ResponseEntity<ErrorResponse> handleOptionNotBelongs (OptionNotBelongsToElectionException ex){
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        String messages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return buildResponse(HttpStatus.BAD_REQUEST, messages);
    }

    private ResponseEntity<ErrorResponse> buildResponse (HttpStatus status, String message){

        var error = ErrorResponse.builder()
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, status);
    }
}
