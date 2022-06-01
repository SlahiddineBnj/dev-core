package com.rest.core.service.v1;

import com.rest.core.dto.response.RequestResponse;
import com.rest.core.dto.ticket.NewReportTicket;
import com.rest.core.dto.ticket.TicketReview;
import org.springframework.http.ResponseEntity;

public interface ReportTicketService {

    ResponseEntity<RequestResponse> createTicket(NewReportTicket ticket) ;

    ResponseEntity<RequestResponse> reviewTicket(TicketReview review) ;

}
