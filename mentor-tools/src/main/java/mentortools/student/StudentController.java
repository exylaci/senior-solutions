package mentortools.student;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get students", description = "Create a list from the all students.")
    public List<StudentDto> getStudents() {
        return service.getStudents();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get students", description = "Find one students in the list of students.")
    @ApiResponse(responseCode = "404", description = "Student not found")
    public StudentDto getStudent(@PathVariable("id") long id) {
        return service.getStudent(id);
    }

    @PostMapping
    @Operation(summary = "Add student", description = "Add a new student to the list of the students.")
    public StudentDto createStudent(
            @RequestBody CreateUpdateStudentCommand command) {
        return service.createStudent(command);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modify student", description = "Modify an existing student.")
    @ApiResponse(responseCode = "404", description = "Student not found")
    public StudentDto updateStudent(
            @PathVariable("id") long id,
            @RequestBody CreateUpdateStudentCommand command) {
        return service.updateStudent(id, command);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete student", description = "Delete an existing student from the list of the students.")
    @ApiResponse(responseCode = "404", description = "Student not found")
    public void deleteStudent(
            @PathVariable("id") long id) {
        service.deleteStudent(id);
    }
}