package org.training360.finalexam.teams;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.training360.finalexam.players.CreatePlayerCommand;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/teams")
@AllArgsConstructor
public class TeamController {
    private final TeamService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TeamDTO> getPlayers() {
        return service.getTeams();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeamDTO createPlayer(
            @Valid @RequestBody CreateTeamCommand command) {
        return service.createTeam(command);
    }

    @PostMapping("/{id}/players")
    @ResponseStatus(HttpStatus.CREATED)
    public TeamDTO createPlayer(
            @PathVariable("id") long id,
            @Valid @RequestBody CreatePlayerCommand command) {
        return service.addNewPlayer(id, command);
    }

    @PutMapping("/{id}/players")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPlayer(
            @PathVariable("id") long id,
            @Valid @RequestBody UpdateWithExistingPlayerCommand command) {
        service.signPlayer(id, command);
    }
}