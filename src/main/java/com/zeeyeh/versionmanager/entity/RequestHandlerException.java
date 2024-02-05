package com.zeeyeh.versionmanager.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RequestHandlerException extends RuntimeException {
    private ErrorConstant errorConstant;

    public RequestHandlerException(ErrorConstant errorConstant) {
        this.errorConstant = errorConstant;
    }
}
