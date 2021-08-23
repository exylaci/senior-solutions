package fightergame.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class NotFound extends AbstractThrowableProblem {
    public NotFound(String url, String title, String message) {
        super(URI.create(url),
                title,
                Status.NOT_FOUND,
                message);
    }
}