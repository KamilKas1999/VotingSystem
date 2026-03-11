package com.kasprzak.kamil.VotingSystem.exceptions;

public class ElectionNotFoundException extends RuntimeException {
    public ElectionNotFoundException(String message) {
        super(message);
    }
}
