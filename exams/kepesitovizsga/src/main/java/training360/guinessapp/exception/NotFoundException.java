package training360.guinessapp.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class NotFoundException extends AbstractThrowableProblem {
    public NotFoundException(String url, String title, String message) {
        super(URI.create(url),
                title,
                Status.NOT_FOUND,
                message);
    }
}