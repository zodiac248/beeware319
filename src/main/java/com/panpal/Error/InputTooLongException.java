package com.panpal.Error;

public class InputTooLongException extends RuntimeException{
    public InputTooLongException() {
    }

    public InputTooLongException(String message) {
        super(message);
    }

    public InputTooLongException(String message, Throwable cause) {
        super(message, cause);
    }

    public InputTooLongException(Throwable cause) {
        super(cause);
    }
}
