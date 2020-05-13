
package com.api.controllers;

import com.api.entities.Game;
import com.api.repositories.GameRepository;
import javassist.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class GameController {
    private final GameRepository gameRepository;

    public GameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @GetMapping("/games")
    public List<Game> greeting() {
        return (List<Game>) this.gameRepository.findAll();
    }

    @PostMapping("/games")
    public Game add(@RequestBody Game newGame) {
        return gameRepository.save(newGame);
    }

    // Single item

    @GetMapping("/games/{id}")
    public Game one(@PathVariable UUID id) throws NotFoundException {

        return gameRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Game with id " + id + "not found "));
    }

    @PutMapping("/games/{id}")
    public Game replacePlayer(@RequestBody Game newGame, @PathVariable UUID id) {

        return gameRepository.findById(id)
                .map(game -> {
                    game.setDescription(newGame.getDescription());
                    return gameRepository.save(game);
                })
                .orElseGet(() -> {
                    newGame.setId(id);
                    return gameRepository.save(newGame);
                });
    }

    @DeleteMapping("/games/{id}")
    public void deleteGame(@PathVariable UUID id) {
        gameRepository.deleteById(id);
    }
}
