package fightergame.contest;

import fightergame.fighter.FighterDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contests")
@AllArgsConstructor
@Tag(name = "Operations on contests")
public class ContestController {
    private final ContestService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get contests", description = "Create a list from all contests.")
    public List<ContestDto> getContests() {
        return service.getContests();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get contest", description = "Get all information about a contest.")
    public ContestDto getContest(
            @PathVariable("id") long id) {
        return service.getContest(id);
    }

    @PostMapping("/{idA}/{idB}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "start contest", description = "Start a new contest and store it into the contests datatable.")
    public ContestDto startContest(
            @PathVariable("idA") long idA,
            @PathVariable("idB") long idB) {
        return service.startContest(idA, idB);
    }

    @GetMapping("/tournament_podium")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get contests", description = "Create a list from all contests.")
    public List<FighterDto> getTournamentPodium() {
        return service.getTournamentPodium();
    }
}