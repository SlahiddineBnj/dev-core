package com.rest.core.repository;

import com.rest.core.Enum.AccountState;
import com.rest.core.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<AppUser, UUID>{
    Optional<AppUser> findByUsername(String username) ;
    List<AppUser> findAllByState(AccountState state) ;
}
