package com.rest.core.dto.authentication.UserDTO;

import com.rest.core.model.AppUser;
import com.rest.core.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {

    private String firstName  ;
    private String lastName ;
    private List<String> roles ;
    // etc

    public static UserDetails convertToDto(AppUser appUser ) {
        return  UserDetails.builder()
                .firstName(appUser.getFirstName())
                .lastName(appUser.getLastName())
                .roles(appUser.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .build() ;
    }
}
