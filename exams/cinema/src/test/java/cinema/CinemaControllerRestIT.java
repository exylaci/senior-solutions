package cinema;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CinemaControllerRestIT {


    @Autowired
    TestRestTemplate template;

    @BeforeEach
    void init() {
        template.delete("/api/cinema");
    }


    @Test
    void testAddNewMovie() {

        MovieDTO result =
                template.postForObject("/api/cinema",
                        new CreateMovieCommand("Titanic", LocalDateTime.of(2021, 6, 30, 12, 30), 120),
                        MovieDTO.class);


        assertEquals("Titanic", result.getTitle());
        assertEquals(2021, result.getDate().getYear());
        assertEquals(120, result.getFreeSpaces());

    }


    @Test
    void testGetMovies() {

        template.postForObject("/api/cinema",
                new CreateMovieCommand("Titanic", LocalDateTime.of(2021, 6, 30, 12, 30), 120),
                MovieDTO.class);

        template.postForObject("/api/cinema",
                new CreateMovieCommand("Batman", LocalDateTime.of(2021, 6, 29, 20, 30), 120),
                MovieDTO.class);


        List<MovieDTO> result = template.exchange(
                "/api/cinema",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<MovieDTO>>() {
                }).getBody();


        assertThat(result)
                .extracting(MovieDTO::getTitle)
                .containsExactly("Titanic", "Batman");
    }

    @Test
    void testGetMoviesByTitle() {

        template.postForObject("/api/cinema",
                new CreateMovieCommand("Titanic", LocalDateTime.of(2021, 6, 30, 12, 30), 120),
                MovieDTO.class);

        template.postForObject("/api/cinema",
                new CreateMovieCommand("Batman", LocalDateTime.of(2021, 6, 29, 20, 30), 120),
                MovieDTO.class);


        List<MovieDTO> result = template.exchange(
                "/api/cinema?title=batman",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<MovieDTO>>() {
                }).getBody();


        assertThat(result)
                .extracting(MovieDTO::getTitle)
                .containsExactly("Batman");
    }


    @Test
    void testCreateNewReservation() {
        template.postForObject("/api/cinema",
                new CreateMovieCommand("Titanic", LocalDateTime.of(2021, 6, 30, 12, 30), 120),
                MovieDTO.class);

        MovieDTO result = template.postForObject("/api/cinema/1/reserve", new CreateReservationCommand(5), MovieDTO.class);


        assertEquals(115, result.getFreeSpaces());


    }

    @Test
    void updateMovie() {
        template.postForObject("/api/cinema",
                new CreateMovieCommand("Titanic", LocalDateTime.of(2021, 6, 30, 12, 30), 120),
                MovieDTO.class);

        template.put("/api/cinema/1", new UpdateDateCommand(LocalDateTime.of(2021, 7, 1, 12, 30)));


        List<MovieDTO> result = template.exchange(
                "/api/cinema",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<MovieDTO>>() {
                }).getBody();


        assertEquals(7, result.get(0).getDate().getMonthValue());
    }

    @Test
    void createMovieWithInvalidName() {
        Problem result = template.postForObject("/api/cinema",
                new CreateMovieCommand("", LocalDateTime.of(2021, 6, 30, 12, 30), 120),
                Problem.class);


        assertEquals(Status.BAD_REQUEST, result.getStatus());


    }

    @Test
    void createMovieWithInvalidMax() {
        Problem result = template.postForObject("/api/cinema",
                new CreateMovieCommand("", LocalDateTime.of(2021, 6, 30, 12, 30), 15),
                Problem.class);


        assertEquals(Status.BAD_REQUEST, result.getStatus());


    }

    @Test
    void notFoundMovieTest(){
        Problem result = template.getForObject("/api/cinema/1", Problem.class);

        assertEquals(URI.create("cinema/not-found"),result.getType());
        assertEquals(Status.NOT_FOUND, result.getStatus());
    }

    @Test
    void updateWithInvalidNumberOfSeats(){
        template.postForObject("/api/cinema",
                new CreateMovieCommand("Titanic", LocalDateTime.of(2021, 6, 30, 12, 30), 120),
                MovieDTO.class);

        Problem result = template.postForObject("/api/cinema/1/reserve", new CreateReservationCommand(121), Problem.class);


        assertEquals(URI.create("cinema/bad-reservation"),result.getType());
        assertEquals(Status.BAD_REQUEST, result.getStatus());
    }
}
