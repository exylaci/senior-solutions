package nav;

import org.zalando.problem.Status;
import org.zalando.problem.AbstractThrowableProblem;

import java.net.URI;

public class IllegalAppointmentException extends AbstractThrowableProblem {

    public IllegalAppointmentException(String message) {
        super(
                URI.create("/api/appointment"),
                "Illegal appointment data",
                Status.NOT_ACCEPTABLE,
                message);    }
}
