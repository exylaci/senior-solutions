package moviespringdatajpa;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping
    public List<MovieDto> getMovies() {
        return service.getMovies();
    }

    @PostMapping
    public MovieDto createMovie(@RequestBody CreateMovieCommand command) {
        return service.createMovie(command);
    }

    @PostMapping("/{id}/rating")
    public MovieDto addRating(@PathVariable("id") long id, @RequestBody AddRatingCommand command) {
        return service.addRating(id, command);
    }
}