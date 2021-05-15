package com.garage.upskills.employeetransformer.exceptions;

public class BadEmployeeDataException extends RuntimeException {
    private static final long serialVersionUID = 2L;

    private String message;

    public BadEmployeeDataException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
