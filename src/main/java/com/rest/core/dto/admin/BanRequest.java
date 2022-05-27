package com.rest.core.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BanRequest {
    private UUID user_id ;
    private int ban_period ;
    private UUID admin_id ;
    private String reason ;
}
