package com.rest.core.controller.v1.API;

import com.rest.core.dto.accountVerification.AccountVerificationRequest;
import com.rest.core.dto.response.RequestResponse;
import com.rest.core.dto.authentication.AuthenticationRequest;
import com.rest.core.dto.authentication.AuthenticationResponse;
import com.rest.core.dto.authentication.SignupRequest;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;

public interface UserAPI {

    ResponseEntity<AuthenticationResponse> login(AuthenticationRequest request) ;
    ResponseEntity<RequestResponse> signup(SignupRequest request) throws MessagingException;
    ResponseEntity<RequestResponse> verifyAccount(AccountVerificationRequest request) ;
}
