package com.rest.core.dto.ticket;


import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketReview {

    private Long ticket_id ;
    private UUID admin_id ;

}
