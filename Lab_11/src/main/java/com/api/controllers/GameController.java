
package com.api.controllers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import com.api.entities.Game;
import com.api.repositories.GameRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
    private final GameRepository gameRepository;

    public GameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @GetMapping("/games")
    public List<Game> greeting() {

        List<Game> actualList = StreamSupport
                .stream(this.gameRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return actualList;
    }
}
