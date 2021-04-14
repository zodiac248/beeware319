package com.panpal.Error;

public class ExceedRangeException extends RuntimeException{
    public ExceedRangeException() {
    }

    public ExceedRangeException(String message) {
        super(message);
    }

    public ExceedRangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceedRangeException(Throwable cause) {
        super(cause);
    }
}
