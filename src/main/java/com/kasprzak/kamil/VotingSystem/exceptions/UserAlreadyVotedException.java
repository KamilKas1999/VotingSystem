package com.kasprzak.kamil.VotingSystem.exceptions;

public class UserAlreadyVotedException extends RuntimeException {
    public UserAlreadyVotedException(String message) {
        super(message);
    }
}
