package com.rest.core.dto.authentication.UserDTO;

import com.rest.core.model.AppUser;
import com.rest.core.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {

    private UUID user_id ;
    private String firstName  ;
    private String lastName ;
    private List<String> roles ;
    private Long ban_counter  ;
    // etc

    public static UserDetails convertToDto(AppUser appUser ) {
        return  UserDetails.builder()
                .user_id(appUser.getId())
                .firstName(appUser.getFirstName())
                .lastName(appUser.getLastName())
                .roles(appUser.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .ban_counter(appUser.getBan_counter())
                .build() ;
    }
}
