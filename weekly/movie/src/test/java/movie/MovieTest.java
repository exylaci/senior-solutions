package movie;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {
    Movie movie = new Movie("title", 200, LocalDate.of(2000, 12, 31));

    @Test
    void getName() {
        assertEquals("title", movie.getName());
    }

    @Test
    void setName() {
        movie.setName("other");
        assertEquals("other", movie.getName());
    }

    @Test
    void getLength() {
        assertEquals(200, movie.getLength());
    }

    @Test
    void setLength() {
        movie.setLength(100);
        assertEquals(100, movie.getLength());
    }

    @Test
    void getReleaseDate() {
        assertEquals(LocalDate.of(2000, 12, 31), movie.getReleaseDate());
    }

    @Test
    void setReleaseDateCorrect() {
        movie.setReleaseDate(LocalDate.of(2001, 1, 1));
        assertEquals(LocalDate.of(2001, 1, 1), movie.getReleaseDate());
    }
}