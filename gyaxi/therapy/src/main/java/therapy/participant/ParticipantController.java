package therapy.participant;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/participants")
@Tag(name = "Operations on participants")
@AllArgsConstructor
public class ParticipantController {
    private final ParticipantService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get participants", description = "Create a list from the all participants.")
    public List<ParticipantDto> getPlayers() {
        return service.getParticipants();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get participant", description = "Get all information from one participant.")
    public ParticipantDto getPlayer(
            @PathVariable("id") long id) {
        return service.getParticipant(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "create participant", description = "Create a new participant and store it into participants datatable.")
    public ParticipantDto createPlayer(
            @JsonProperty String name) {
        return service.createParticipant(name);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "404", description = "Participant not found")
    @Operation(summary = "modify a player", description = "Modify the details of an existing player.")
    public ParticipantDto updateParticipant(
            @PathVariable("id") long id,
            @Valid @RequestBody UpdateParticipantCommand command) {
        return service.updateParticipant(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "delete a participant", description = "Delete an existing participant from the participants datatable.")
    public void deletePlayer(
            @PathVariable("id") long id) {
        service.deleteParticipant(id);
    }
}
