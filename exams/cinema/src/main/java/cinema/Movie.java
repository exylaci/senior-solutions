package cinema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    private Long id;
    private String title;
    private LocalDateTime date;
    private int max;
    private int freeSpaces;

    public Movie(Long id, CreateMovieCommand command) {
        this.id = id;
        title = command.getTitle();
        date = command.getDate();
        max = command.getMax();
        freeSpaces = max;
    }

    public void reservation(int people) {
        if (freeSpaces < people) {
            throw new IllegalArgumentException("Not enough free place!");
        }
        freeSpaces -= people;
    }
}