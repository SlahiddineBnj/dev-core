package com.rest.core.repository;

import com.rest.core.model.AccountBanData;
import com.rest.core.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountBanDataRepository extends JpaRepository<AccountBanData,Long> {
    AccountBanData findByUser(AppUser user) ;
}
