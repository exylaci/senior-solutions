package training360.guinessapp.recorder;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import training360.guinessapp.dto.RecorderCreateCommand;
import training360.guinessapp.dto.RecorderDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/recorders")
@AllArgsConstructor
public class RecorderController {
    private final RecorderService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecorderDto createRecorder(
            @Valid @RequestBody RecorderCreateCommand command) {
        return service.createRecorder(command);
    }
}