package com.rest.core.repository;

import com.rest.core.model.AccountBanData;
import com.rest.core.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountBanDataRepository extends JpaRepository<AccountBanData,Long> {
    AccountBanData findByUserId(UUID user_id) ;
    void deleteAccountBanDataByUserId(UUID user_id) ;
}
