
package com.api.controllers;

import java.util.List;

import com.api.entities.Game;
import com.api.services.GameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private GameService gameService = GameService.getInstance();

    @GetMapping("/games")
    public List<Game> greeting(){
        return gameService.get();
    }
}
