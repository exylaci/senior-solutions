package org.training360.finalexam.players;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {
    private final PlayerService service;

    public PlayerController(PlayerService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PlayerDTO> getPlayers() {
        return service.getPlayers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlayerDTO createPlayer(
            @Valid @RequestBody CreatePlayerCommand command) {
        return service.createPlayer(command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePlayer(
            @PathVariable("id") long id) {
        service.deletePlayer(id);
    }
}