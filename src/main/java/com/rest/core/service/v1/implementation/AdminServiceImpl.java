package com.rest.core.service.v1.implementation;

import com.rest.core.Enum.AccountState;
import com.rest.core.dto.admin.BanRequest;
import com.rest.core.dto.response.RequestResponse;
import com.rest.core.exception.CaughtException;
import com.rest.core.model.AccountBanData;
import com.rest.core.model.AppUser;
import com.rest.core.repository.AccountBanDataRepository;
import com.rest.core.repository.UserRepository;
import com.rest.core.service.v1.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Period;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository ;
    @Autowired
    private AccountBanDataRepository accountBanDataRepository ;



    @Override
    public ResponseEntity<RequestResponse> banUser(BanRequest request) {
        boolean user_exists = userRepository.existsById(request.getUser_id());
        if (!user_exists)
            throw new CaughtException("INCORRECT_DATA","User does not exist !") ;
        boolean admin_exists = userRepository.existsById(request.getAdmin_id());
        if (!admin_exists)
            throw new CaughtException("INCORRECT_DATA","Admin does not exist !") ;
        // all good
        AppUser user = userRepository.findById(request.getUser_id()).get() ;
        AccountBanData banData = AccountBanData.builder()
                .userId(request.getUser_id())
                .adminId(request.getAdmin_id())
                .ban_date(LocalDateTime.now())
                .banned_until(LocalDateTime.now().plus(Period.ofDays(request.getBan_period())))
                .reason(request.getReason())
                .build();
        accountBanDataRepository.save(banData) ;
        user.setState(AccountState.BANNED);
        userRepository.save(user) ;
        RequestResponse response = RequestResponse.builder()
                .message(String.format("User %s have been banned for %s days ",user.getUsername(),
                        request.getBan_period()))
                .timestamp(LocalDateTime.now())
                .build() ;
        return new ResponseEntity<>(response , HttpStatus.OK);
    }
}
