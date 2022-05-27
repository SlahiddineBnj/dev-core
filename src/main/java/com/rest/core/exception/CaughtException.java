package com.rest.core.exception;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CaughtException extends RuntimeException{

    private String message ;
    private LocalDateTime timestamp ;

    public CaughtException(String message) {
        this.message = message ;
        this.timestamp  = LocalDateTime.now() ;
    }
}
