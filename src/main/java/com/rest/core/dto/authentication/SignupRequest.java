package com.rest.core.dto.authentication;

import com.rest.core.model.AppUser;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    private String username ;
    private String password ;
    private String firstName ;
    private String lastName ;
    private String email ;

    public static AppUser convertToEntity(SignupRequest request){
        return AppUser.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .password(new BCryptPasswordEncoder().encode(request.getPassword()))
                .email(request.getEmail())
                .build();
    }
}
