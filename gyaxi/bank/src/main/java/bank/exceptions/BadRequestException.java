package bankremake.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class BadRequestException extends AbstractThrowableProblem {
    public BadRequestException(String url, String title, String message) {
        super(URI.create(url),
                title,
                Status.BAD_REQUEST,
                message);
    }
}