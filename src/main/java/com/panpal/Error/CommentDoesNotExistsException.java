package com.panpal.Error;

public class CommentDoesNotExistsException extends RuntimeException{
    public CommentDoesNotExistsException() {
    }

    public CommentDoesNotExistsException(String message) {
        super(message);
    }

    public CommentDoesNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommentDoesNotExistsException(Throwable cause) {
        super(cause);
    }
}
