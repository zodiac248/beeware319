package com.panpal.Error;

public class FloorNoLongerExists extends RuntimeException{
    public FloorNoLongerExists() {
    }

    public FloorNoLongerExists(String message) {
        super(message);
    }

    public FloorNoLongerExists(String message, Throwable cause) {
        super(message, cause);
    }

    public FloorNoLongerExists(Throwable cause) {
        super(cause);
    }
}
