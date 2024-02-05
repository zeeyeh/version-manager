package com.zeeyeh.versionmanager.interceptors;

import com.zeeyeh.versionmanager.entity.RequestHandlerException;
import com.zeeyeh.versionmanager.entity.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RequestErrorHandler {

    @ExceptionHandler(RequestHandlerException.class)
    public ResponseEntity<Response> RequestHandlerExceptionHandler(RequestHandlerException exception) {
        return Response.server_error(exception.getErrorConstant());
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Response> ExceptionHandler() {
//        return Response.forbidden();
//    }
}
