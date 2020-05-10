package com.api.entities;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "game_tbl")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String description;

    public Game() {
    }

    public Game(String description) {
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String name) {
        this.description = name;
    }

    @Override
    public String toString() {
        return "Game{" +
                ", name='" + description + '\'' +
                '}';
    }
}