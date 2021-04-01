package com.panpal.Error;

public class DeskNoLongerExistsException extends RuntimeException{
    public DeskNoLongerExistsException() {
    }

    public DeskNoLongerExistsException(String message) {
        super(message);
    }

    public DeskNoLongerExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeskNoLongerExistsException(Throwable cause) {
        super(cause);
    }
}
