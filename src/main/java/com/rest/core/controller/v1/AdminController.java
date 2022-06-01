package com.rest.core.controller.v1;

import com.rest.core.constant.Constant;
import com.rest.core.controller.v1.API.AdminAPI;
import com.rest.core.dto.admin.BanRequest;
import com.rest.core.dto.authentication.UserDTO.UserDetails;
import com.rest.core.dto.response.RequestResponse;
import com.rest.core.dto.ticket.TicketReview;
import com.rest.core.service.v1.AdminService;
import com.rest.core.service.v1.ReportTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(Constant.API_PATH+ "/admin")
public class AdminController implements AdminAPI {

    @Autowired
    private AdminService adminService ;
    @Autowired
    private ReportTicketService reportTicketService ;

    @Override
    @PostMapping(value = "/ban")
    public ResponseEntity<RequestResponse> banUser(@RequestBody BanRequest request) {
        return adminService.banUser(request);
    }

    @Override
    @PutMapping(value = "/unban/{user_id}")
    public ResponseEntity<RequestResponse> unbanUser(@PathVariable UUID user_id) {
        return adminService.unbanUser(user_id);
    }

    @Override
    @GetMapping(value = "/search")
    public Page<UserDetails> searchUsers(@RequestParam String query,
                                         @RequestParam int page,
                                         @RequestParam int size) {
        return adminService.searchUsers(query, page, size);
    }

    @Override
    @PutMapping(value = "/ticket/review")
    public ResponseEntity<RequestResponse> reviewReportTicket(@RequestBody TicketReview review) {
        return reportTicketService.reviewTicket(review);
    }
}
