package com.rest.core.controller.v1.API;

import com.rest.core.dto.admin.BanRequest;
import com.rest.core.dto.authentication.UserDTO.UserDetails;
import com.rest.core.dto.response.RequestResponse;
import com.rest.core.dto.ticket.TicketReview;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface AdminAPI {

    ResponseEntity<RequestResponse> banUser(BanRequest request) ;
    ResponseEntity<RequestResponse> unbanUser(UUID user_id) ;
    Page<UserDetails> searchUsers(String query , int page , int size ) ;
    ResponseEntity<RequestResponse> reviewReportTicket(TicketReview review) ;
}
