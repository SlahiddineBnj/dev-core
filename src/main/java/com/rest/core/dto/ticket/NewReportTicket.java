package com.rest.core.dto.ticket;


import com.rest.core.Enum.ReportTicketStatus;
import com.rest.core.model.ReportTicket;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class NewReportTicket {
    private UUID reporter_user_id ;
    private UUID reported_user_id ;
    private String description ;

    public ReportTicket convertToEntity(){
        return ReportTicket.builder()
                .reporter_user_id(this.getReporter_user_id())
                .reported_user_id(this.getReported_user_id())
                .description(this.getDescription())
                .status(ReportTicketStatus.UNTREATED)
                .build();
    }
}
