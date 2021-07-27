package moviespringdatajpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @Transient
    private Double rating;

    @ElementCollection
    @CollectionTable(name = "ratings", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "rating")
    private List<Integer> ratings;

    public Movie(String title) {
        this.title = title;
    }

    public void addRating(int rating) {
        if (ratings == null) {
            ratings = new ArrayList<>();
        }
        ratings.add(rating);
    }
}