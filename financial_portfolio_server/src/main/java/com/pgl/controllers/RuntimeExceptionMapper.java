package com.pgl.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class RuntimeExceptionMapper extends RuntimeException {

    public RuntimeExceptionMapper() {
        super();
    }

    public RuntimeExceptionMapper(String message) {
        super(message);
    }

}
