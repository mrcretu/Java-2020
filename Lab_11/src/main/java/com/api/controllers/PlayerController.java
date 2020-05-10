
package com.api.controllers;

import com.api.entities.Player;
import com.api.exceptions.PlayerNotFoundException;
import com.api.repositories.PlayerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class PlayerController {

    private final PlayerRepository playerRepository;

    public PlayerController(PlayerRepository gameRepository) {
        this.playerRepository = gameRepository;
    }

    @GetMapping("/players")
    public List<Player> all() {
        return (List<Player>) playerRepository.findAll();
    }

    @PostMapping("/players")
    public Player add(@RequestBody Player newPlayer) {
        return playerRepository.save(newPlayer);
    }

    // Single item

    @GetMapping("/players/{id}")
    public Player one(@PathVariable UUID id) {

        return playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException(id));
    }

    @PutMapping("/players/{id}")
    public Player replacePlayer(@RequestBody Player newPlayer, @PathVariable UUID id) {

        return playerRepository.findById(id)
                .map(player -> {
                    player.setName(newPlayer.getName());
                    return playerRepository.save(player);
                })
                .orElseGet(() -> {
                    newPlayer.setId(id);
                    return playerRepository.save(newPlayer);
                });
    }

    @DeleteMapping("/players/{id}")
    public void deletePlayer(@PathVariable UUID id) {
        playerRepository.deleteById(id);
    }
}
