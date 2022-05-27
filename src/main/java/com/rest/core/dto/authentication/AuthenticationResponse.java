package com.rest.core.dto.authentication;

import com.rest.core.dto.authentication.UserDTO.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {

    private UUID user_id ;
    private String token ;
    private Date token_expiry ;
    private UserDetails userDetails ;

}
