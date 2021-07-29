package author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // @Column(name = "author_id")
    private Long id;

    // @NotNull
    // @Column(name = "author_name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")   //, orphanRemoval = true)
    private Set<Book> books;

    public Author(String name) {
        this.name = name;
    }

    public void addBook(Book book) {
        if (books == null) {
            books = new HashSet<>();
        }
        books.add(book);
        book.setAuthor(this);
    }
}