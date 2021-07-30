package author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id")
    private Long id;
    private String isbn;
    private String title;

    @ManyToOne(cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "author_id")
    private Author author;

    public Book(String isbn, String title) {
        this.isbn = isbn;
        this.title = title;
    }
}