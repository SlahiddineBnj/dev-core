package com.rest.core.service.v1;

import com.rest.core.dto.accountVerification.AccountVerificationRequest;
import com.rest.core.dto.response.RequestResponse;
import com.rest.core.dto.authentication.AuthenticationRequest;
import com.rest.core.dto.authentication.AuthenticationResponse;
import com.rest.core.dto.authentication.SignupRequest;
import com.rest.core.dto.ticket.NewReportTicket;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URISyntaxException;

public interface UserService {

    ResponseEntity<AuthenticationResponse> login(AuthenticationRequest request)  ;

    ResponseEntity<RequestResponse> signup(SignupRequest request) throws MessagingException, URISyntaxException, IOException;

    ResponseEntity<RequestResponse> verifyAccount(AccountVerificationRequest request) ;

    ResponseEntity<RequestResponse> submitReportTicket(NewReportTicket ticket) ;
}
