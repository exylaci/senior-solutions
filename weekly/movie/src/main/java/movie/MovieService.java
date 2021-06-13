package movie;

import java.util.*;
import java.util.stream.Collectors;

public class MovieService {
    private List<Movie> movies = new ArrayList<>();

    public void save(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("Parameter cannot be null!");
        }
        movies.add(movie);
    }

    public List<Movie> findMovieByNamePart(String part) {
        if (part == null) {
            throw new IllegalArgumentException("Parameter cannot be null!");
        }

        String findThis = part.toLowerCase();

        return movies
                .stream()
                .filter(movie -> movie.getName() != null && movie.getName().toLowerCase().contains(findThis))
                .collect(Collectors.toList());
    }

    public Optional<Movie> findNewest() {
        return movies
                .stream()
                .max(Comparator.comparing(Movie::getReleaseDate, Comparator.nullsFirst(Comparator.naturalOrder())));
    }

    public List<Movie> getMovies() {
        return new ArrayList<>(movies);
    }

    public Integer totalLength() {
        return movies
                .stream()
                .reduce(0, (a, b) -> a + b.getLength(), (x, y) -> x + y);
    }

    public Map<Integer, Integer> countingFilmsByYearOfRelease() {
        return movies
                .stream()
                .filter(movie -> movie.getReleaseDate() != null)
                .collect(Collectors.toMap(m -> m.getReleaseDate().getYear(), n -> 1, Integer::sum));
    }

    public Optional<Movie> findShortestFilm() {
        return movies
                .stream()
                .filter(movie -> movie.getLength() > 0)
                .min(Comparator.comparing(Movie::getLength));
    }
}
