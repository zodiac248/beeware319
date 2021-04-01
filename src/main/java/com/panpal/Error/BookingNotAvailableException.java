package com.panpal.Error;

public class BookingNotAvailableException extends RuntimeException{
    public BookingNotAvailableException() {
    }

    public BookingNotAvailableException(String message) {
        super(message);
    }

    public BookingNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookingNotAvailableException(Throwable cause) {
        super(cause);
    }
}
