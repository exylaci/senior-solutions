package author;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthorService {
    private AuthorRepository authorRepository;
    //    private BookRepository bookRepository;
    private ModelMapper modelMapper;

    public List<AuthorDto> getAuthors() {
//        Type targetListType = new TypeToken<List<AuthorDto>>(){}.getType();
//        List<Author> authors = authorRepository.findAll();
//        return modelMapper.map(authors, targetListType);

        return authorRepository
                .findAll()
                .stream()
                .map(a -> modelMapper.map(a, AuthorDto.class))
                .toList();
    }

    public AuthorDto createAuthor(CreateAuthorCommand command) {
        Author author = new Author(command.getName());
        authorRepository.save(author);
        return modelMapper.map(author, AuthorDto.class);
    }

    @Transactional
    public AuthorDto addBook(long id, AddBookCommand command) {
        Author author = authorRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("There is no author with this id: " + id));
        Book book = new Book(command.getIsbn(), command.getTitle());

        // bookRepository.save(book);
        author.addBook(book);

        return modelMapper.map(author, AuthorDto.class);
    }

    public void deleteAuthor(Long id) {
        // Author author = authorRepository
        //     .findById(id)
        //     .orElseThrow(() -> new IllegalArgumentException("There is no author with this id: " + id));
        // for(Book book:author.getBooks()){
        //     bookRepository.delete(book);
        // }
        authorRepository.deleteById(id);
    }
}