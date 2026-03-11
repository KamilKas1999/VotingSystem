package com.kasprzak.kamil.VotingSystem.exceptions;

public class OptionNotBelongsToElectionException extends RuntimeException {
    public OptionNotBelongsToElectionException(String message) {
        super(message);
    }
}
