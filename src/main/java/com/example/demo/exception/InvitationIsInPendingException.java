package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvitationIsInPendingException extends RuntimeException {


    @Serial
    private static final long serialVersionUID = 1L;

    public InvitationIsInPendingException(String message) {
        super(message);
    }
}
