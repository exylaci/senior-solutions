package fightergame.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class BadRequest extends AbstractThrowableProblem {
    public BadRequest(String url, String title, String message) {
        super(URI.create(url),
                title,
                Status.BAD_REQUEST,
                message);
    }
}