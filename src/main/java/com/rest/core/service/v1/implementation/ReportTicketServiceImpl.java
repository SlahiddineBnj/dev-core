package com.rest.core.service.v1.implementation;

import com.rest.core.Enum.ReportTicketStatus;
import com.rest.core.dto.response.RequestResponse;
import com.rest.core.dto.ticket.NewReportTicket;
import com.rest.core.dto.ticket.TicketReview;
import com.rest.core.exception.CaughtException;
import com.rest.core.model.ReportTicket;
import com.rest.core.repository.ReportTicketRepository;
import com.rest.core.service.v1.ReportTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReportTicketServiceImpl implements ReportTicketService {

    @Autowired
    private ReportTicketRepository  reportTicketRepository ;

    @Override
    public ResponseEntity<RequestResponse> createTicket(NewReportTicket ticket) {
        reportTicketRepository.save(ticket.convertToEntity()) ;
        RequestResponse response = RequestResponse.builder()
                .message("Report Ticket has been submitted successfully !")
                .timestamp(LocalDateTime.now())
                .extra("")
                .build() ;
        return new ResponseEntity<>(response, HttpStatus.OK) ;
    }

    @Override
    public ResponseEntity<RequestResponse> reviewTicket(TicketReview review) {
        boolean ticket_exists = reportTicketRepository.existsById(review.getTicket_id()) ;
        if (!ticket_exists)
            throw new CaughtException("ERROR","Ticket does not exist") ;
        // ticket does exist
        ReportTicket ticket = reportTicketRepository.findById(review.getTicket_id()).get() ;
        ticket.setStatus(ReportTicketStatus.CLOSED);
        ticket.setAdmin_id(review.getAdmin_id());

        RequestResponse response = RequestResponse.builder()
                .message("Ticket has been review successfully ! ")
                .timestamp(LocalDateTime.now())
                .extra("")
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
