package telephoneregister.numbertype;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/number-types")
@AllArgsConstructor
@Tag(name = "Operations on phone number type")
public class NumberTypeController {
    private final NumberTypeService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get phone number types", description = "Create a list from the all phone number types.")
    public List<NumberTypeDto> getNumberTypes() {
        return service.getNumberTypes();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "create phone number type", description = "Create a new phone number type and store it into the numbertypes datatable.")
    public NumberTypeDto createNumberType(
            @JsonProperty String numbertype) {
        return service.createNumberType(numbertype);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "404", description = "Phone number type id not found")
    @Operation(summary = "modify a phone number type", description = "Modify the name of an existing phone number type.")
    public NumberTypeDto updateNumberType(
            @PathVariable("id") long id,
            @JsonProperty String numbertype) {
        return service.updateNumberType(id, numbertype);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "404", description = "Phone number type id not found")
    @Operation(summary = "delete a phone number type", description = "Delete an existing phone number type from the products datatable.")
    public NumberTypeDto deleteNumberType(
            @PathVariable("id") long id) {
        return service.deleteNumberType(id);
    }
}