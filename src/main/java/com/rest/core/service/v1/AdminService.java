package com.rest.core.service.v1;

import com.rest.core.dto.admin.BanRequest;
import com.rest.core.dto.response.RequestResponse;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface AdminService {

    ResponseEntity<RequestResponse> banUser(BanRequest request) ;
}
