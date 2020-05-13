package com.api.exceptions;

import java.util.UUID;

public class GameNotFoundException extends RuntimeException {

    public GameNotFoundException(UUID id) {
        super("Could not find employee " + id);
    }
}
