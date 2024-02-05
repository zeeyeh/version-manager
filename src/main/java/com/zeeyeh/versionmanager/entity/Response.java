package com.zeeyeh.versionmanager.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SuperBuilder
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Response {
    private int code;
    private String message;
    private Object data;

    public static ResponseEntity<Response> success() {
        return ResponseEntity.status(HttpStatus.OK).body(Response.builder()
                .code(0)
                .message("success")
                .build());
    }

    public static ResponseEntity<Response> success(Object data) {
        return ResponseEntity.status(HttpStatus.OK).body(Response.builder()
                .code(0)
                .message("success")
                .data(data)
                .build());
    }

    public static ResponseEntity<Response> success(Object data, HttpHeaders headers) {
        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .body(Response.builder()
                .code(0)
                .message("success")
                .data(data)
                .build());
    }

    public static ResponseEntity<Response> error(int code, String message) {
        return ResponseEntity.status(HttpStatus.OK).body(Response.builder()
                .code(code)
                .message(message)
                .build());
    }

    public static ResponseEntity<Response> error(int code, String message, Object data) {
        return ResponseEntity.status(HttpStatus.OK).body(Response.builder()
                .code(code)
                .message(message)
                .data(data)
                .build());
    }

    public static ResponseEntity<Response> error(ErrorConstant errorConstant) {
        return ResponseEntity.status(HttpStatus.OK).body(Response.builder()
                .code(errorConstant.getCode())
                .message(errorConstant.getMessage())
                .build());
    }

    public static ResponseEntity<Response> error(ErrorConstant errorConstant, Object data) {
        return ResponseEntity.status(HttpStatus.OK).body(Response.builder()
                .code(errorConstant.getCode())
                .message(errorConstant.getMessage())
                .data(data)
                .build());
    }

    public static ResponseEntity<Response> not_found() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public static ResponseEntity<Response> not_found(ErrorConstant errorConstant) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.builder()
                .code(errorConstant.getCode())
                .message(errorConstant.getMessage())
                .build());
    }

    public static ResponseEntity<Response> not_found(ErrorConstant errorConstant, Object data) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.builder()
                .code(errorConstant.getCode())
                .message(errorConstant.getMessage())
                .data(data)
                .build());
    }

    public static ResponseEntity<Response> server_error() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    public static ResponseEntity<Response> server_error(ErrorConstant errorConstant) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.builder()
                .code(errorConstant.getCode())
                .message(errorConstant.getMessage())
                .build());
    }

    public static ResponseEntity<Response> server_error(ErrorConstant errorConstant, Object data) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.builder()
                .code(errorConstant.getCode())
                .message(errorConstant.getMessage())
                .data(data)
                .build());
    }

    public static ResponseEntity<Response> forbidden() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    public static ResponseEntity<Response> forbidden(ErrorConstant errorConstant) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Response.builder()
                .code(errorConstant.getCode())
                .message(errorConstant.getMessage())
                .build());
    }

    public static ResponseEntity<Response> forbidden(ErrorConstant errorConstant, Object data) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Response.builder()
                .code(errorConstant.getCode())
                .message(errorConstant.getMessage())
                .data(data)
                .build());
    }
}
