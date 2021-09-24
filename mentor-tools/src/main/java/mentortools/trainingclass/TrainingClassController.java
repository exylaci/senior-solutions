package mentortools.trainingclass;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainingclasses")
public class TrainingClassController {
    private TrainingClassService service;

    public TrainingClassController(TrainingClassService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get training classes", description = "Create a list from the all training classes.")
    public List<TrainingClassDto> getTrainingClasses() {
        return service.getTrainingClasses();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get training class", description = "Find one training class in the list of training classes.")
    @ApiResponse(responseCode = "404", description = "Training class not found")
    public TrainingClassDto getTrainingClasses(@PathVariable("id") long id) {
        return service.getTrainingClass(id);
    }

    @PostMapping
    @Operation(summary = "Add training class", description = "Add a new training class to the list of the training classes.")
    public TrainingClassDto createTrainingClass(
            @RequestBody CreateUpdateTrainingClassCommand command) {
        return service.createTrainingClass(command);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modify training class", description = "Modify an existing training class.")
    @ApiResponse(responseCode = "404", description = "Training class not found")
    public TrainingClassDto updateTrainingClass(
            @PathVariable("id") long id,
            @RequestBody CreateUpdateTrainingClassCommand command) {
        return service.updateTrainingClass(id, command);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete training class", description = "Delete an existing training class from the list of the training classes.")
    @ApiResponse(responseCode = "404", description = "Training class not found")
    public void deleteTrainingClass(
            @PathVariable("id") long id) {
        service.deleteTrainingClass(id);
    }
}