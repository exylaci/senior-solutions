package telephoneregister.person;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/persons")
@AllArgsConstructor
@Tag(name = "Operations on peerson")
public class PersonController {
    private final PersonService service;

    @GetMapping("/names")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get persons", description = "Create a list from the name of all persons.")
    public List<String> getPlayers() {
        return service.getAllPersonsNames();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get person", description = "Get all information about a person.")
    public PersonDto getPerson(
            @PathVariable("id") long id) {
        return service.getPerson(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "create person", description = "Create a new person and store it into the persons datatable.")
    public PersonDto createPerson(
            @Valid @RequestBody CreatePersonCommand command) {
        return service.createPerson(command);
    }

    @PutMapping("/{id}/phones")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "add phone", description = "Add phone number to an existing person and store it into the phones datatable.")
    public PersonDto addPhone(
            @PathVariable("id") long id,
            @Valid @RequestBody AddPhoneCommand command) {
        return service.addPhone(id, command);
    }

    @PutMapping("/{id}/addresses")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "add address", description = "Add address to an existing person and store it into the addresses datatable.")
    public PersonDto addAddress(
            @PathVariable("id") long id,
            @Valid @RequestBody AddAddressCommand command) {
        return service.addAddress(id, command);
    }

    @PutMapping("/{id}/emails")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "add e-mail", description = "Add e-mail to an existing person and store it into the emails datatable.")
    public PersonDto addEmail(
            @PathVariable("id") long id,
            @Valid @RequestBody AddEmailCommand command) {
        return service.addEmail(id, command);
    }
}