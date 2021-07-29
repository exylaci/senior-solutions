package author;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    private AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @GetMapping
    public List<AuthorDto> getAuthors() {
        return service.getAuthors();
    }

    @PostMapping
    public AuthorDto createAuthor(@RequestBody CreateAuthorCommand command) {
        return service.createAuthor(command);
    }

    @PostMapping("/{id}")
    public AuthorDto addBook(
            @PathVariable("id") long id,
            @RequestBody AddBookCommand command) {
        return service.addBook(id, command);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(
            @PathVariable("id") long id) {
        service.deleteAuthor(id);
    }
}