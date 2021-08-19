package therapy.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class NotFoundException extends AbstractThrowableProblem {
    public NotFoundException(String url, String title, String detail) {
        super(URI.create(url),
                title,
                Status.NOT_FOUND,
                detail);
    }
}