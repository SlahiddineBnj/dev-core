package com.rest.core.service.v1;

import com.rest.core.dto.admin.BanRequest;
import com.rest.core.dto.authentication.UserDTO.UserDetails;
import com.rest.core.dto.response.RequestResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface AdminService {

    ResponseEntity<RequestResponse> banUser(BanRequest request) ;

    ResponseEntity<RequestResponse> unbanUser(UUID user_id) ;

    Page<UserDetails> searchUsers(String searchQuery , int page , int size) ;
}
