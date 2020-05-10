
package com.api.controllers;

import java.util.concurrent.atomic.AtomicLong;

import com.api.entities.Player;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {

    private static final String template = "Hello, I'm player %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/player")
    public Player greeting(@RequestParam(value = "name", defaultValue = "Marius") String name) {
        return new Player(counter.incrementAndGet(), String.format(template, name));
    }
}
