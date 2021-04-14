package com.panpal.Error;

public class DuplicateDeskException extends RuntimeException{
    public DuplicateDeskException() {
    }

    public DuplicateDeskException(String message) {
        super(message);
    }

    public DuplicateDeskException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateDeskException(Throwable cause) {
        super(cause);
    }
}
