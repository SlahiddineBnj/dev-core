package com.rest.core.model;


import com.rest.core.Enum.ReportTicketStatus;
import com.rest.core.util.Auditable;
import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
//@Document
public class ReportTicket extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    private UUID reporter_user_id ;

    private UUID reported_user_id ;

    private String ticket_description ;

    private ReportTicketStatus status ;

    private UUID admin_id ;


}
