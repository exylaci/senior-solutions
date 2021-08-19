package therapy.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class InvalidDataException extends AbstractThrowableProblem {
    public InvalidDataException(String url, String title, String detail) {
        super(URI.create(url),
                title,
                Status.BAD_REQUEST,
                detail);
    }
}