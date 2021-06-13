package movie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MovieServiceTest {
    MovieService movieService = new MovieService();

    Movie movie1 = new Movie("HP1", 251, LocalDate.of(2000, 12, 12));
    Movie movie2 = new Movie("HP2", 252, LocalDate.of(2001, 12, 12));
    Movie movie3 = new Movie("HP3", 253, LocalDate.of(2002, 12, 12));
    Movie movie4 = new Movie("HP4", 254, LocalDate.of(2003, 12, 12));

    @BeforeEach
    void setUp() {
        movieService.save(movie1);
        movieService.save(movie2);
        movieService.save(movie3);
        movieService.save(movie4);
        movieService.save(new Movie());
    }


    @Test
    void saveTest() {
        movieService.save(movie4);
        assertEquals(6, movieService.getMovies().size());
    }


    @Test
    void testFindNewest() {
        Movie movie = movieService.findNewest().get();
        assertEquals(movie4, movie);
    }

    @Test
    void testFindNewestOnlyEmptyMovie() {
        MovieService movieService = new MovieService();
        movieService.save(new Movie());
        Movie movie = movieService.findNewest().get();
        assertNull(movie.getName());
        assertNull(movie.getReleaseDate());
    }

    @Test
    void testFindNewestEmptyList() {
        assertEquals(Optional.empty(), new MovieService().findNewest());
    }


    @Test
    void testFindMovieByNamePartListIsEmpty() {
        MovieService movieService1 = new MovieService();
        List<Movie> movieList = movieService1.findMovieByNamePart("P");
        assertEquals(0, movieList.size());
    }

    @Test
    void testFindMovieByNamePartNotFind() {
        List<Movie> movieList = movieService.findMovieByNamePart("LLL");
        assertEquals(0, movieList.size());
    }

    @Test
    void testFindMovieByNamePartValid() {
        List<Movie> movieList = movieService.findMovieByNamePart("hp");
        assertEquals(4, movieList.size());
    }

    @Test
    void testFindMovieByNamePartOneResult() {
        List<Movie> movieList = movieService.findMovieByNamePart("4");
        assertEquals(List.of(movie4), movieList);
    }

    @Test
    void findMovieByNamePartWithNull() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> movieService.findMovieByNamePart(null));
    }

    @Test
    void totalLength() {
        assertEquals(1010, movieService.totalLength());
    }

    @Test
    void totalLengthEmptyList() {
        assertEquals(0, new MovieService().totalLength());
    }


    @Test
    void countingFilmsByYearOfRelease() {
        Map<Integer, Integer> result = movieService.countingFilmsByYearOfRelease();

        assertEquals(4, result.size());
        assertEquals(1, result.get(2002));
    }

    @Test
    void countingFilmsByYearOfReleaseSame() {
        MovieService movieService=new MovieService();
        movieService.save(movie1);
        movieService.save(movie1);
        movieService.save(movie1);

        Map<Integer, Integer> result = movieService.countingFilmsByYearOfRelease();

        assertEquals(1, result.size());
        assertEquals(3, result.get(2000));
    }


    @Test
    void shortestFilm() {
        assertEquals(movie1, movieService.findShortestFilm().get());
    }

    @Test
    void shortestFilmWidthInvalidLength() {
        MovieService movieService = new MovieService();
        movieService.save(new Movie());

        assertEquals(Optional.empty(), movieService.findShortestFilm());
    }

    @Test
    void shortestFilmEmptyList() {
        assertEquals(Optional.empty(), new MovieService().findShortestFilm());
    }
}