package com.panpal.Error;

public class DuplicateFloorException extends RuntimeException{
    public DuplicateFloorException() {
    }

    public DuplicateFloorException(String message) {
        super(message);
    }

    public DuplicateFloorException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateFloorException(Throwable cause) {
        super(cause);
    }
}
