package movie;

import java.time.LocalDate;

public class Movie {
    private String name;
    private int length;
    private LocalDate releaseDate;

    public Movie() {
    }

    public Movie(String name, int length, LocalDate releaseDate) {
        this.name = name;
        this.length = length;
        this.releaseDate = releaseDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
