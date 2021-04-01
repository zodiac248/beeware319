package com.panpal.Error;

public class BookingNotExistsException extends RuntimeException{
    public BookingNotExistsException() {
    }

    public BookingNotExistsException(String message) {
        super(message);
    }

    public BookingNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookingNotExistsException(Throwable cause) {
        super(cause);
    }
}
