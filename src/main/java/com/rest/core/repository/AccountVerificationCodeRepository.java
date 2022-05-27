package com.rest.core.repository;

import com.rest.core.model.AccountVerificationCode;
import com.rest.core.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountVerificationCodeRepository extends JpaRepository<AccountVerificationCode,Long> {
    Optional<AccountVerificationCode> findByUser(AppUser user) ;
}
