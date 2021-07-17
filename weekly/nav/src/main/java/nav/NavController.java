package nav;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class NavController {
    private NavService service;

    public NavController(NavService service) {
        this.service = service;
    }

    @GetMapping("/types")
    @ResponseStatus(HttpStatus.OK)
    public List<CaseTypeDTO> getCaseTypes() {
        System.out.println(service.getCaseTypes());
        return service.getCaseTypes();
    }

    @PostMapping("/appointments")
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentDTO createAppointment(@Valid @RequestBody CreateAppointmentCommand command) {
        return service.createAppointment(command);
    }
}
