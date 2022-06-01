package com.rest.core.controller.v1;

import com.rest.core.constant.Constant;
import com.rest.core.controller.v1.API.UserAPI;
import com.rest.core.dto.accountVerification.AccountVerificationRequest;
import com.rest.core.dto.response.RequestResponse;
import com.rest.core.dto.authentication.AuthenticationRequest;
import com.rest.core.dto.authentication.AuthenticationResponse;
import com.rest.core.dto.authentication.SignupRequest;
import com.rest.core.dto.ticket.NewReportTicket;
import com.rest.core.service.v1.ReportTicketService;
import com.rest.core.service.v1.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping(value = Constant.API_PATH + "/user")
public class UserController implements UserAPI {

    @Autowired
    private UserService userService ;
    @Autowired
    private ReportTicketService reportTicketService ;

    @Override
    @PostMapping(value = "/auth")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody  AuthenticationRequest request) {
        return userService.login(request);
    }

    @Override
    @PostMapping(value = "/register")
    public ResponseEntity<RequestResponse> signup(@RequestBody SignupRequest request) throws MessagingException, URISyntaxException, IOException {
        return userService.signup(request);
    }

    @Override
    @PostMapping(value = "/auth/verify")
    public ResponseEntity<RequestResponse> verifyAccount(@RequestBody AccountVerificationRequest request) {
        return userService.verifyAccount(request);
    }

    @Override
    @PostMapping(value = "/ticket/submit")
    public ResponseEntity<RequestResponse> submitReportTicket(@RequestBody NewReportTicket ticket) {
        return reportTicketService.createTicket(ticket);
    }
}
