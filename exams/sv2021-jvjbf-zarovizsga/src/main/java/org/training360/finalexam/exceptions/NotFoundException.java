package org.training360.finalexam.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class NotFoundException extends AbstractThrowableProblem {
    public NotFoundException(String url, String message) {
        super(URI.create(url),
                message,
                Status.NOT_FOUND,
                message);
    }
}