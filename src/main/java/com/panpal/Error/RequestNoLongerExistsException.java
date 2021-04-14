package com.panpal.Error;

public class RequestNoLongerExistsException extends RuntimeException{
    public RequestNoLongerExistsException() {
    }

    public RequestNoLongerExistsException(String message) {
        super(message);
    }

    public RequestNoLongerExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestNoLongerExistsException(Throwable cause) {
        super(cause);
    }
}
