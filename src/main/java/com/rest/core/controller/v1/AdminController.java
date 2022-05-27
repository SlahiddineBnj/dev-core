package com.rest.core.controller.v1;

import com.rest.core.constant.Constant;
import com.rest.core.controller.v1.API.AdminAPI;
import com.rest.core.dto.admin.BanRequest;
import com.rest.core.dto.response.RequestResponse;
import com.rest.core.service.v1.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constant.API_PATH+ "/admin")
public class AdminController implements AdminAPI {

    @Autowired
    private AdminService adminService ;

    @Override
    @PostMapping("/ban")
    public ResponseEntity<RequestResponse> banUser(@RequestBody BanRequest request) {
        return adminService.banUser(request);
    }
}
