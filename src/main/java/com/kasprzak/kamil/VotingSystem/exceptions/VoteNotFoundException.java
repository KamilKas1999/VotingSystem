package com.kasprzak.kamil.VotingSystem.exceptions;

public class VoteNotFoundException extends RuntimeException {
    public VoteNotFoundException(String message) {
        super(message);
    }
}
