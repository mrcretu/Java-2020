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
         Game G1 = new Game("First game!");
         Game G2 = new Game("Second game!");
        games = Arrays.asList(G1, G2);
    }
}
