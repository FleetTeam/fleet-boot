package org.fleet.common.exception;

public class FleetBootException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FleetBootException(String message) {
        super(message);
    }

    public FleetBootException(Throwable cause) {
        super(cause);
    }

    public FleetBootException(String message, Throwable cause) {
        super(message, cause);
    }
}
