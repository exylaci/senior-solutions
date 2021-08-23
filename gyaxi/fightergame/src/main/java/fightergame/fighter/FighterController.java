package fightergame.fighter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/fighters")
@AllArgsConstructor
@Tag(name = "Operations on fighters")
public class FighterController {
    private final FighterService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get fighters", description = "Create a list from all fighters.")
    public List<FighterDto> getFighters() {
        return service.getFighters();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get fighter", description = "Get all information about a fighter.")
    public FighterDto getFighter(
            @PathVariable("id") long id) {
        return service.getFighter(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "create fighter", description = "Create a new fighter and store it into the fighters datatable.")
    public FighterDto createPlayer(
            @Valid @RequestBody CreateUpdateFighterCommand command) {
        return service.createFighter(command);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "404", description = "Fighter not found")
    @Operation(summary = "modify a fighter", description = "Modify the details of an existing fighter.")
    public FighterDto updatePlayer(
            @PathVariable("id") long id,
            @Valid @RequestBody CreateUpdateFighterCommand command) {
        return service.updateFighter(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "delete a fighter", description = "Delete an existing fighter from the fighters datatable.")
    public void deleteFighter(
            @PathVariable("id") long id) {
        service.deleteFighter(id);
    }
}