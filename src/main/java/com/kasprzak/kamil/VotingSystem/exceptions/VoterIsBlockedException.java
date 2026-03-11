package com.kasprzak.kamil.VotingSystem.exceptions;

public class VoterIsBlockedException extends RuntimeException {
    public VoterIsBlockedException(String message) {
        super(message);
    }
}
