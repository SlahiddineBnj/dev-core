package com.rest.core.dto.accountVerification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountVerificationRequest {

    private UUID user_id ;
    private String verification_code ;

}
