package com.panpal.Error;

public class BuildingNoLongerExistsException extends RuntimeException{
    public BuildingNoLongerExistsException() {
    }

    public BuildingNoLongerExistsException(String message) {
        super(message);
    }

    public BuildingNoLongerExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuildingNoLongerExistsException(Throwable cause) {
        super(cause);
    }
}
