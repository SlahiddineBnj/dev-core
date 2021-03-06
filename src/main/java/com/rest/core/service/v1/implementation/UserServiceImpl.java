package com.rest.core.service.v1.implementation;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.rest.core.constant.Constant;
import com.rest.core.dto.accountVerification.AccountVerificationRequest;
import com.rest.core.dto.mailing.EmailData;
import com.rest.core.dto.response.RequestResponse;
import com.rest.core.dto.authentication.AuthenticationRequest;
import com.rest.core.dto.authentication.AuthenticationResponse;
import com.rest.core.dto.authentication.SignupRequest;
import com.rest.core.Enum.AccountState;
import com.rest.core.dto.ticket.NewReportTicket;
import com.rest.core.exception.CaughtException;
import com.rest.core.model.AccountBanData;
import com.rest.core.model.AccountVerificationCode;
import com.rest.core.model.AppUser;
import com.rest.core.model.Role;
import com.rest.core.repository.AccountBanDataRepository;
import com.rest.core.repository.AccountVerificationCodeRepository;
import com.rest.core.repository.UserRepository;
import com.rest.core.security.MyUserDetailsService;
import com.rest.core.service.v1.EmailService;
import com.rest.core.service.v1.ReportTicketService;
import com.rest.core.service.v1.RoleService;
import com.rest.core.service.v1.UserService;
import com.rest.core.util.HTMLEngine;
import com.rest.core.validation.Validator;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager ;
    @Autowired
    private MyUserDetailsService myUserDetailsService ;
    @Autowired
    private UserRepository userRepository ;
    @Autowired
    private AccountVerificationCodeRepository accountVerificationCodeRepository ;
    @Autowired
    private RoleService roleService ;
    @Autowired
    private AccountBanDataRepository accountBanDataRepository ;
    @Autowired
    private EmailService emailService ;
    @Autowired
    private HTMLEngine htmlEngine ;
    @Autowired
    private ReportTicketService reportTicketService ;


    @Override
    public ResponseEntity<AuthenticationResponse> login(AuthenticationRequest request) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        } catch (BadCredentialsException e){
            throw new CaughtException("INCORRECT_CREDENTIALS","You have entered incorrect credentials !") ;
        }
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(request.getUsername());
        AppUser appuser = userRepository.findByUsername(userDetails.getUsername()).get() ;
        switch (appuser.getState()) {
            case UNVERIFIED -> throw new CaughtException("UNVERIFIED_ACCOUNT","Account is not verified yet !",appuser.getId().toString());
            case BANNED -> {
                AccountBanData banData = accountBanDataRepository.findByUserId(appuser.getId());
                throw new CaughtException("BANNED",String.format("Account is banned until %s",
                        banData.getBanned_until()));
            }
        }
        Algorithm algorithm = Algorithm.HMAC256(Constant.SECRET.getBytes(StandardCharsets.UTF_8)) ;
        Date expiryDate = new Date(System.currentTimeMillis()+ Constant.TOKEN_DURATION *60*1000) ;

        String access_token = JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(expiryDate)
                .withClaim("roles",userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm) ;


        AuthenticationResponse response = AuthenticationResponse.builder()
                .token(access_token)
                .token_expiry(expiryDate)
                .userDetails(com.rest.core.dto.authentication.UserDTO.UserDetails.convertToDto(appuser))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RequestResponse> signup(SignupRequest request) throws MessagingException, URISyntaxException, IOException {
        List<String> errors_list = Validator.ValidateSignup(request) ;
        // we validate the request
        if(errors_list.size() != 0 ){
            throw new CaughtException("ERROR","Unable to register new account , reasons "+errors_list) ;
        }
        if (userRepository.findByUsername(request.getUsername()).isPresent()){
            throw new CaughtException("ERROR","An account with the same username already exists !") ;
        }else {
            // username does not exist
            AppUser appUser = SignupRequest.convertToEntity(request) ;
            Role user_role = roleService.getRoleByName("USER") ;
            appUser.setRoles(List.of(user_role));
            appUser.setState(AccountState.UNVERIFIED);
            appUser = userRepository.save(appUser) ;

            String verification_code = String.valueOf((int)(Math.random() * (999999 - 100000)) + 100000) ;

            Document htmlDoc = htmlEngine.getDocument("signup_letter.html") ;
            String htmlString = htmlEngine.injectValues(htmlDoc,
                    Map.of("#firstName", appUser.getFirstName(),
                    "#username", appUser.getUsername(),
                    "#verification_code",verification_code)) ;

            EmailData emailData = EmailData.builder()
                    .to(request.getEmail())
                    .subject("Registration Successful !")
                    .body(htmlString)
                    .timestamp(LocalDateTime.now())
                    .build();

            emailService.sendEmail(emailData,true);

            accountVerificationCodeRepository.save(AccountVerificationCode.builder()
                    .user(appUser)
                    .verification_code(verification_code)
                    .build()) ;

            RequestResponse response = RequestResponse.builder()
                    .message("You account been registered successfully !")
                    .extra(appUser.getId().toString())
                    .timestamp(LocalDateTime.now())
                    .build();

            return new ResponseEntity<>(response,HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<RequestResponse> verifyAccount(AccountVerificationRequest request) {
        boolean user_exist = userRepository.existsById(request.getUser_id());
        if (!user_exist)
            throw new CaughtException("INVALID_USER","User does not exist !");
        AppUser user = userRepository.findById(request.getUser_id()).get();
        Optional<AccountVerificationCode> codeOptional =
                accountVerificationCodeRepository.findByUser(user);
        if (codeOptional.isEmpty())
            throw new CaughtException("INVALID_CODE","Verification code does not exist !");
        else {
            // we need to check the code
            if (Objects.equals(codeOptional.get().getVerification_code(),
                    request.getVerification_code())) {
                user.setState(AccountState.ACTIVE);
                userRepository.save(user) ;
                accountVerificationCodeRepository.delete(codeOptional.get());
                RequestResponse response = RequestResponse.builder()
                        .message("Your account has been verified successfully !")
                        .timestamp(LocalDateTime.now())
                        .build();
                return new ResponseEntity<>(response, HttpStatus.OK);
            }else {
                throw new CaughtException("INCORRECT_ATTEMPT","Incorrect Attempt !") ;
            }
        }
    }

    @Override
    public ResponseEntity<RequestResponse> submitReportTicket(NewReportTicket ticket) {
        return reportTicketService.createTicket(ticket) ;
    }

}
