package movie2;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Movie {

    private final long id;
    private final String title;
    private final int length;
    private double rating;
    private List<Integer> ratings = new ArrayList<>();

    public Movie(long id, String title, int length) {
        this.id = id;
        this.title = title;
        this.length = length;
    }

    public void addRating(int rating) {
        ratings.add(rating);
        this.rating = ratings.stream().mapToInt(i -> i).average().getAsDouble();
    }
}
