package com.rest.core.dto.mailing;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailData {

    private String to ;
    private String subject ;
    private Object body ;
    private LocalDateTime timestamp ;

}
