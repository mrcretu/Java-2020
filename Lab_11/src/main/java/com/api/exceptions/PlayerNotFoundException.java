package com.api.exceptions;

import java.util.UUID;

public class PlayerNotFoundException extends RuntimeException {

    public PlayerNotFoundException(UUID id) {
        super("Could not find employee " + id);
    }
}