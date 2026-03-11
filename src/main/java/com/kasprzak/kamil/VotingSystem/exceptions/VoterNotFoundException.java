package com.kasprzak.kamil.VotingSystem.exceptions;

public class VoterNotFoundException extends RuntimeException {
    public VoterNotFoundException(String message) {
        super(message);
    }
}
