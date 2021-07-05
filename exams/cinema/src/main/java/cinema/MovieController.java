package cinema;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cinema")
public class MovieController {
    private MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MovieDTO> getMovies(
            @RequestParam Optional<String> title) {
        return service.getMovies(title);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MovieDTO getMovie(
            @PathVariable("id") Long id) {
        return service.getMovie(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDTO addMovie(
            @Valid @RequestBody CreateMovieCommand command) {
        return service.addMovie(command);
    }

    @PostMapping("/{id}/reserve")
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDTO reservation(
            @PathVariable("id") Long id,
            @Valid @RequestBody CreateReservationCommand command) {
        return service.reservation(id, command);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDTO reservation(
            @PathVariable("id") Long id,
            @Valid @RequestBody UpdateDateCommand command) {
        return service.updateDate(id, command);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovies() {
        service.deleteMovies();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Problem> handleExceptionNotFind(IllegalArgumentException e) {
        if (e.getMessage().startsWith("Not enough free place!")) {
            Problem problem = Problem
                    .builder()
                    .withType(URI.create("cinema/bad-reservation"))
                    .withTitle("Not enoug free seats!")
                    .withStatus(Status.BAD_REQUEST)
                    .withDetail(e.getMessage())
                    .build();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                    .body(problem);
        } else {
            Problem problem = Problem
                    .builder()
                    .withType(URI.create("cinema/not-found"))
                    .withTitle("Movie not find!")
                    .withStatus(Status.NOT_FOUND)
                    .withDetail(e.getMessage())
                    .build();
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                    .body(problem);
        }
    }
}