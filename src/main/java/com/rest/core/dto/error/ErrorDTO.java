package com.rest.core.dto.error;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDTO {
    private String status ;
    private String message  ;
    private LocalDateTime timestamp ;
}
