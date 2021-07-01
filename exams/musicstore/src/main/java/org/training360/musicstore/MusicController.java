package org.training360.musicstore;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/instruments")
public class MusicController {
    private MusicStoreService service;

    public MusicController(MusicStoreService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public InstrumentDTO getInstrument(@PathVariable("id") long id) {
        return service.getInstrument(id);
    }

    @GetMapping
    public List<InstrumentDTO> getInstruments(@RequestParam Optional<String> brand, @RequestParam Optional<Integer> price) {
        return service.getInstruments(brand, price);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InstrumentDTO createInstrument(@Valid @RequestBody CreateInstrumentCommand command) {
        return service.createInstrument(command);
    }

    @PutMapping("/{id}")
    public void updateInstrumentPrice(@PathVariable("id") long id, @Valid @RequestBody UpdatePriceCommand command) {
        service.updateInstrumentPrice(id, command);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInstrument() {
        service.deleteAllInstrument();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInstrument(@PathVariable("id") long id) {
        service.deleteInstrument(id);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Problem> handleExceptionNotFind(IllegalArgumentException e) {
        Problem problem = Problem.builder()
                .withType(URI.create("instruments/not-found"))
                .withTitle("Can't find nstrument!")
                .withStatus(Status.NOT_FOUND)
                .withDetail(e.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Problem> handleExceptionNotFind(MethodArgumentNotValidException e) {
        Problem problem = Problem.builder()
                .withType(URI.create("instruments/not-valid"))
                .withTitle("Price cannot be negative!")
                .withStatus(Status.BAD_REQUEST)
                .withDetail(e.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }


}
