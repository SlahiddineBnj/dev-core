package com.rest.core.controller.v1.API;

import com.rest.core.dto.admin.BanRequest;
import com.rest.core.dto.response.RequestResponse;
import org.springframework.http.ResponseEntity;

public interface AdminAPI {

    ResponseEntity<RequestResponse> banUser(BanRequest request) ;
}
