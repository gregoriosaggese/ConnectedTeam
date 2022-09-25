package com.prd.ConnectedTeam.gamesRules;

public class DuplicateGameNameException extends RuntimeException {
    public DuplicateGameNameException(String message) {
        super(message);
    }
}
