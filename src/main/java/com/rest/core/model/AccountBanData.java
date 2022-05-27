package com.rest.core.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountBanData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;


    private UUID userId   ;
    private UUID adminId ;
    private LocalDate ban_date ;
    private LocalDate banned_until ;
    private String reason ;

}
