
package com.api.controllers;

import java.util.List;

import com.api.entities.Player;
import com.api.services.PlayerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {

    private PlayerService playerService = PlayerService.getInstance();

    @GetMapping("/players")
    public List<Player> greeting() {
        return playerService.get();
    }
}
