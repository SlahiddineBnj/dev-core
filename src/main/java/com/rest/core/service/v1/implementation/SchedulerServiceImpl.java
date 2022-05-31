package com.rest.core.service.v1.implementation;

import com.rest.core.Enum.AccountState;
import com.rest.core.model.AccountBanData;
import com.rest.core.model.AppUser;
import com.rest.core.repository.AccountBanDataRepository;
import com.rest.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SchedulerServiceImpl {

    @Autowired
    private UserRepository userRepository ;
    @Autowired
    private AccountBanDataRepository accountBanDataRepository ;


    @Scheduled(fixedDelay = 1000*60*60*24)
    private void unbanUsersWhenTimeComes(){
        List<AppUser> bannedUsers = userRepository.findAllByState(AccountState.BANNED) ;
        for (AppUser user: bannedUsers) {
            AccountBanData banData = accountBanDataRepository.findByUserId(user.getId()) ;
            if (LocalDateTime.now().isAfter(banData.getBanned_until())){
                accountBanDataRepository.delete(banData) ;
                user.setState(AccountState.ACTIVE);
                userRepository.save(user) ;
            }
        }
    }
}
