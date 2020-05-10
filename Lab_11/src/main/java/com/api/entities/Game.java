package com.api.entities;

import java.util.UUID;

public class Game {

    private final String id;
    private final String description;

    public Game(String desc) {
        this.id = getId();
        this.description = desc;
    }

    public String getId() {
        return UUID.randomUUID().toString();
    }

    public String getDescription() {
        return description;
    }
}
