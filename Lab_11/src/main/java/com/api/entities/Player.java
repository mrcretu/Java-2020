package com.api.entities;

import java.util.UUID;

public class Player {

    private final String id;
    private final String name;

    public Player(String name) {
        this.id = getId();
        this.name = name;
    }

    public String getId() {
        return UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }
}
