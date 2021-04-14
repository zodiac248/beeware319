package com.panpal.Error;

public class DuplicateTopicException extends RuntimeException{
    public DuplicateTopicException() {
    }

    public DuplicateTopicException(String message) {
        super(message);
    }

    public DuplicateTopicException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateTopicException(Throwable cause) {
        super(cause);
    }
}
