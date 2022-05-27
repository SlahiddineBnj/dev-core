package com.rest.core.exception;


import com.rest.core.dto.error.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CaughtException.class)
    public ResponseEntity<?> handleException(CaughtException exception) {
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(ErrorDTO.builder()
                .status(exception.getStatus())
                .message(exception.getMessage())
                .extra(exception.getExtra())
                .timestamp(exception.getTimestamp())
                .build(), badRequest);
    }

}
