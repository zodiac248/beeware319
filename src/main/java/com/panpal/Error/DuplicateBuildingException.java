package com.panpal.Error;

public class DuplicateBuildingException extends RuntimeException{
    public DuplicateBuildingException() {
    }

    public DuplicateBuildingException(String message) {
        super(message);
    }

    public DuplicateBuildingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateBuildingException(Throwable cause) {
        super(cause);
    }
}
