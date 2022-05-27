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
import com.rest.core.enum22.AccountState;
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
import com.rest.core.service.v1.RoleService;
import com.rest.core.service.v1.UserService;
import com.rest.core.validation.Validator;
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
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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



    @Override
    public ResponseEntity<AuthenticationResponse> login(AuthenticationRequest request) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        } catch (BadCredentialsException e){
            throw new CaughtException("You have entered incorrect credentials !") ;
        }
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(request.getUsername());
        AppUser appuser = userRepository.findByUsername(userDetails.getUsername()).get() ;
        switch (appuser.getState()){
            case UNVERIFIED:
                throw new CaughtException("Account is not verified yet !") ;
            case BANNED:
                AccountBanData banData = accountBanDataRepository.findByUser(appuser) ; 
                //todo - finish here
                throw new CaughtException("Account is banned !") ;
        }
        Algorithm algorithm = Algorithm.HMAC256(Constant.SECRET.getBytes(StandardCharsets.UTF_8)) ;
        Date expiryDate = new Date(System.currentTimeMillis()+ Constant.TOKEN_DURATION *60*1000) ;

        String access_token = JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(expiryDate)
                .withClaim("roles",userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm) ;


        AuthenticationResponse response = AuthenticationResponse.builder()
                .user_id(appuser.getId())
                .token(access_token)
                .token_expiry(expiryDate)
                .userDetails(com.rest.core.dto.authentication.UserDTO.UserDetails.convertToDto(appuser))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RequestResponse> signup(SignupRequest request) throws MessagingException {
        List<String> errors_list = Validator.ValidateSignup(request) ;
        // we validate the request
        if(errors_list.size() != 0 ){
            throw new CaughtException("Unable to register new account , reasons "+errors_list) ;
        }
        if (userRepository.findByUsername(request.getUsername()).isPresent()){
            throw new CaughtException("An account with the same username already exists !") ;
        }else {
            // username does not exist
            AppUser appUser = SignupRequest.convertToEntity(request) ;
            Role user_role = roleService.getRoleByName("USER") ;
            appUser.setRoles(List.of(user_role));
            appUser.setState(AccountState.UNVERIFIED);
            appUser = userRepository.save(appUser) ;

            String verification_code = String.valueOf((int)(Math.random() * (999999 - 100000)) + 100000) ;

            EmailData emailData = EmailData.builder()
                    .to(request.getEmail())
                    .subject("Registration")
                    .body("PLACEHOLDER")
                    .timestamp(LocalDateTime.now())
                    .build();
//            emailService.sendEmail(emailData);

            accountVerificationCodeRepository.save(AccountVerificationCode.builder()
                    .user(appUser)
                    .verification_code(verification_code)
                    .build()) ;

            RequestResponse response = RequestResponse.builder()
                    .status_code(HttpStatus.OK)
                    .message("You account been registered successfully !")
                    .timestamp(LocalDateTime.now())
                    .build();

            return new ResponseEntity<>(response,HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<RequestResponse> verifyAccount(AccountVerificationRequest request) {
        boolean user_exist = userRepository.existsById(request.getUser_id());
        if (!user_exist)
            throw new CaughtException("User does not exist !");
        AppUser user = userRepository.findById(request.getUser_id()).get();
        Optional<AccountVerificationCode> codeOptional =
                accountVerificationCodeRepository.findByUser(user);
        if (codeOptional.isEmpty())
            throw new CaughtException("Verification code does not exist !");
        else {
            // we need to check the code
            if (Objects.equals(codeOptional.get().getVerification_code(),
                    request.getVerification_code())) {
                user.setState(AccountState.ACTIVE);
                userRepository.save(user) ;
                RequestResponse response = RequestResponse.builder()
                        .message("Your account has been verified successfully !")
                        .status_code(HttpStatus.OK)
                        .timestamp(LocalDateTime.now())
                        .build();
                return new ResponseEntity<>(response, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(RequestResponse.builder()
                        .message("Incorrect Attempt")
                        .status_code(HttpStatus.BAD_REQUEST)
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.BAD_REQUEST);
            }
        }
    }

}
