package cinema;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MovieService {
    private List<Movie> movies = new ArrayList<>();
    private AtomicLong idGenerator = new AtomicLong();
    private ModelMapper modelMapper;

    public MovieService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public MovieDTO getMovie(Long id) {
        return modelMapper.map(findMovie(id), MovieDTO.class);
    }

    public List<MovieDTO> getMovies(Optional<String> title) {
        return movies
                .stream()
                .filter(movie -> title.isEmpty() || movie.getTitle().equalsIgnoreCase(title.get()))
                .map(movie -> modelMapper.map(movie, MovieDTO.class))
                .toList();
    }

    public MovieDTO addMovie(CreateMovieCommand command) {
        Movie movie = new Movie(idGenerator.incrementAndGet(), command);
        movies.add(movie);
        return modelMapper.map(movie, MovieDTO.class);
    }

    public MovieDTO reservation(Long id, CreateReservationCommand command) {
        Movie movie = findMovie(id);
        int index = movies.indexOf(movie);
        movie.reservation(command.getSeats());
        movies.set(index, movie);
        return modelMapper.map(movie, MovieDTO.class);
    }

    public MovieDTO updateDate(Long id, UpdateDateCommand command) {
        Movie movie = findMovie(id);
        int index = movies.indexOf(movie);
        movie.setDate(command.getDate());
        movies.set(index, movie);
        return modelMapper.map(movie, MovieDTO.class);
    }

    public void deleteMovies() {
        movies.clear();
        idGenerator = new AtomicLong();
    }

    private Movie findMovie(Long id) {
        return movies
                .stream()
                .filter(movie -> movie.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Can't find this movie id: " + id));
    }
}