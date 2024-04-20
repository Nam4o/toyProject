package com.nam4o.myweb.common.exception;

import ch.qos.logback.core.spi.ErrorCodes;
import lombok.Getter;

@Getter
public class Exceptions extends RuntimeException {
    private ErrorCode errorCode;

    public Exceptions(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
