package training360.guinessapp.worldrecord;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import training360.guinessapp.dto.BeatWorldRecordCommand;
import training360.guinessapp.dto.BeatWorldRecordDto;
import training360.guinessapp.dto.WorldRecordCreateCommand;
import training360.guinessapp.dto.WorldRecordDto;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/worldrecords")
@AllArgsConstructor
public class WorldRecordController {
    private final WorldRecordService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorldRecordDto createWorldRecord(
            @Valid @RequestBody WorldRecordCreateCommand command) {
        return service.createWorldRecord(command);
    }

    @PutMapping("/{id}/beatrecords")
    @ResponseStatus(HttpStatus.OK)
    public BeatWorldRecordDto beatWorldRecord(
            @PathVariable("id") Long id,
            @Valid @RequestBody BeatWorldRecordCommand command) {
        return service.beatWorldRecord(id, command);
    }
}