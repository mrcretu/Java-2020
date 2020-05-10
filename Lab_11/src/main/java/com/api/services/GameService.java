package com.api.services;

import com.api.entities.Game;

import java.util.Arrays;
import java.util.List;

public class GameService {

    private static GameService instance = null;
    private List<Game> games;
    private GameService() {
        seedGameList();
    }

    public static GameService getInstance() {
        if (instance == null) {
            instance = new GameService();
        }
        return instance;
    }

    public List<Game> get() {
        return games;
    }

    private void  seedGameList() {
    }
}
