package moviespringdatajpa;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
@AllArgsConstructor
public class MovieService {
    private MovieRepository repository;
    private ModelMapper modelMapper;

    public List<MovieDto> getMovies() {
        Type targetListType = new TypeToken<List<MovieDto>>() {
        }.getType();

        List<Movie> movies = repository.findAll();
        movies.forEach(Movie::calculateRating);

        return modelMapper.map(movies, targetListType);
    }

    public MovieDto createMovie(CreateMovieCommand command) {
        Movie movie = new Movie(command.getTitle());
        repository.save(movie);
        return modelMapper.map(movie, MovieDto.class);
    }

    public MovieDto addRating(long id, AddRatingCommand command) {
        Movie movie = repository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("There is no movie with id: " + id));

        movie.addRating(command.getRating());
        repository.save(movie);
        movie.calculateRating();
        return modelMapper.map(movie, MovieDto.class);
    }
}