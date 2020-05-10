package com.api.services;

import com.api.entities.Player;

import java.util.Arrays;
import java.util.List;

public class PlayerService {

    private static PlayerService instance = null;
    private List<Player> games;
    private PlayerService() {
        seedPlayersList();
    }

    public static PlayerService getInstance() {
        if (instance == null) {
            instance = new PlayerService();
        }
        return instance;
    }

    public List<Player> get() {
        return games;
    }

    private void seedPlayersList() {
        Player P1 = new Player("Marius Cretu!");
        Player P2 = new Player("Silviu Gabor!");
        games = Arrays.asList(P1, P2);
    }
}
