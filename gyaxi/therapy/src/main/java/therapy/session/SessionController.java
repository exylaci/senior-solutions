package therapy.session;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@Tag(name = "Operations on sessions")
@AllArgsConstructor
public class SessionController {
    private final SessionService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get sessions", description = "Create a list from the all sessions.")
    public List<SessionDto> getSessions() {
        return service.getSessions();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get session", description = "Get all information from one session.")
    public SessionDto getSession(
            @PathVariable("id") long id) {
        return service.getSession(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "create session", description = "Create a new session and store it into sessions datatable.")
    public SessionDto createSession(
            @Valid @RequestBody CreateUpdateSessionCommand command) {
        return service.createSession(command);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "404", description = "Session not found")
    @Operation(summary = "modify a session", description = "Modify the details of an existing session.")
    public SessionDto updateSession(
            @PathVariable("id") long id,
            @Valid @RequestBody CreateUpdateSessionCommand command) {
        return service.updateSession(id, command);
    }

    @PutMapping("/{id}/participants")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "404", description = "Session or Participant not found")
    @Operation(summary = "add participants to session", description = "Add a participant to an existing session.")
    public SessionDto addParticipant(
            @PathVariable("id") long id,
            @Valid @RequestBody AddParticipantCommand command) {
        return service.addParticipant(id, command);
    }

    @DeleteMapping("/{session_id}/participants/{participant_id}")
    @Operation(summary = "remove participant from session", description = "Cancel the registration of a participant from a session.")
    public SessionDto removeParticipant(
            @PathVariable("session_id") long sessionId,
            @PathVariable("participant_id") long participantId) {
        return service.removeParticipant(sessionId, participantId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "delete a session", description = "Delete only the existing session from the sessions datatable and from its participants.")
    public void deleteSession(
            @PathVariable("id") long id) {
        service.deleteSession(id);
    }
}