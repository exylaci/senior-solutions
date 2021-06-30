package movie2;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MovieService {
    private AtomicLong idGenerator = new AtomicLong();
    private ModelMapper modelMapper;

    private List<Movie> movies = Collections.synchronizedList(new ArrayList<>(List.of(
            new Movie(idGenerator.incrementAndGet(), "Angyalbőrben", 45),
            new Movie(idGenerator.incrementAndGet(), "Angyalok városa", 95),
            new Movie(idGenerator.incrementAndGet(), "Nem vagyunk mi angyalok", 60),
            new Movie(idGenerator.incrementAndGet(), "Kisváros", 45)
    )));

    public MovieService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<MovieDto> getMovies(Optional<String> title) {
        return movies
                .stream()
                .filter(movie -> title.isEmpty() || movie.getTitle().toLowerCase().contains(title.get().toLowerCase()))
                .map(movie -> modelMapper.map(movie, MovieDto.class))
                .toList();
    }

    public MovieDto getMovie(long id) {
        return modelMapper.map(findMovieById(id), MovieDto.class);
    }

    public MovieDto addMovie(CreateMovieCommand create) {
        if (create.getTitle().isEmpty() || create.getLength() == 0) {
            throw new IllegalArgumentException("Title and length are must!");
        }
        Movie movie = new Movie(
                idGenerator.incrementAndGet(),
                create.getTitle(),
                create.getLength());
        movies.add(movie);
        return modelMapper.map(movie, MovieDto.class);
    }

    public MovieDto addRating(long id, UpdateMovieCommand command) {
        if (command.getRating() == 0) {
            throw new IllegalArgumentException("Rating is a must!");
        }

        Movie movie = findMovieById(id);
        movie.addRating(command.getRating());
        return modelMapper.map(movie, MovieDto.class);
    }

    private Movie findMovieById(long id) {
        return movies
                .stream()
                .filter(movie -> movie.getId() == id)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("There is no movie with this ID: " + id));
    }

    public void deleteMovie(long id) {
        Movie movie = findMovieById(id);
        movies.remove(movie);
    }
}
