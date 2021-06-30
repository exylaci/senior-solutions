package movie2;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping
    public List<MovieDto> getMovies(@RequestParam Optional<String> title) {
        return service.getMovies(title);
    }

    @GetMapping("/{id}")
    public MovieDto getMovie(@PathVariable("id") long id) {
        return service.getMovie(id);
    }

    @PostMapping
    public MovieDto addMovie(@RequestBody CreateMovieCommand command) {
        return service.addMovie(command);
    }

    @PutMapping("/{id}/rating")
    public MovieDto addRating(@PathVariable("id") long id, @RequestBody UpdateMovieCommand command) {
        return service.addRating(id, command);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable("id") long id) {
        service.deleteMovie(id);
    }
}
