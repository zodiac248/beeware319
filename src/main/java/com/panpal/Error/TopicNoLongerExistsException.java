package com.panpal.Error;

public class TopicNoLongerExistsException extends RuntimeException{
    public TopicNoLongerExistsException() {
    }

    public TopicNoLongerExistsException(String message) {
        super(message);
    }

    public TopicNoLongerExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TopicNoLongerExistsException(Throwable cause) {
        super(cause);
    }
}
