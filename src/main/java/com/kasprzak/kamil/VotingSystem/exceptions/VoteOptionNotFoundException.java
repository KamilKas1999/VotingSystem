package com.kasprzak.kamil.VotingSystem.exceptions;

public class VoteOptionNotFoundException extends RuntimeException {
    public VoteOptionNotFoundException(String message) {
        super(message);
    }
}
