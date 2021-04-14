package com.panpal.Error;

public class MailNoLongerExistsException extends RuntimeException{
    public MailNoLongerExistsException() {
    }

    public MailNoLongerExistsException(String message) {
        super(message);
    }

    public MailNoLongerExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailNoLongerExistsException(Throwable cause) {
        super(cause);
    }
}
