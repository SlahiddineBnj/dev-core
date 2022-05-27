package com.rest.core.exception;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CaughtException extends RuntimeException{

    private String status ;
    private String message ;
    private String extra ;
    private LocalDateTime timestamp ;

    public CaughtException(String status , String message) {
        this.status = status ;
        this.message = message ;
        this.timestamp  = LocalDateTime.now() ;
        this.extra = "" ;
    }

    public CaughtException(String status , String message , String extra){
        this.status = status ;
        this.message = message ;
        this.timestamp  = LocalDateTime.now() ;
        this.extra = extra ;
    }
}
