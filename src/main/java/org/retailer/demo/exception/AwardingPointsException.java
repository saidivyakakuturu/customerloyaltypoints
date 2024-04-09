package org.retailer.demo.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AwardingPointsException extends RuntimeException{
    public AwardingPointsException() {
        super();
    }
    public AwardingPointsException(String message, Throwable cause) {
        super(message, cause);
    }
    public AwardingPointsException(String message) {
        super(message);
    }
    public AwardingPointsException(Throwable cause) {
        super(cause);
    }
}
