package fleamarket;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fleamarket/advertisement")
public class AdvertisementController {
    private AdvertisementService service;

    public AdvertisementController(AdvertisementService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AdvertisementDTO> getAdvertistements(
            @RequestParam Optional<String> category,
            @RequestParam Optional<String> word) {
        return service.getAdvertisements(category, word);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AdvertisementDTO getAdvertistement(
            @PathVariable("id") Long id) {
        return service.getAdvertisement(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdvertisementDTO createAdvertisement(
            @Valid @RequestBody CreateAdvertisementCommand command) {
        return service.addNewAdvertisement(command);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AdvertisementDTO updateAdvertisement(
            @PathVariable("id") long id,
            @Valid @RequestBody UpdateAdvertisementCommand command) {
        return service.updateAdvertisement(id, command);
    }

    @DeleteMapping("/all")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllAdvertisement() {
        service.deleteAllAdvertisement();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAdvertisement(
            @PathVariable("id") long id) {
        service.deleteAdvertisement(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAdvertisement(
            @Valid @RequestParam Optional<LumberCategory> category) {
        service.deleteAdvertisement(category);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Problem> handleExceptionNotFind(IllegalArgumentException e) {
        Problem problem = Problem
                .builder()
                .withType(URI.create("advertisement/not-found"))
                .withTitle("Advertisement not find!")
                .withStatus(Status.NOT_FOUND)
                .withDetail(e.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }
}