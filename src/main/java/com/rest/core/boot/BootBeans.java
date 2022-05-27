package com.rest.core.boot;


import com.rest.core.model.AppUser;
import com.rest.core.model.Role;
import com.rest.core.repository.RoleRepository;
import com.rest.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class BootBeans {

    @Autowired
    private RoleRepository roleRepository ;
    @Autowired
    private UserRepository userRepository ;

    @Bean
    void createPermissionRoles(){
        Role user_role = Role.builder()
                .name("USER")
                .build();
        Role admin_role = Role.builder()
                .name("ADMIN")
                .build();

        // an anti-redundancy check
        if(roleRepository.findByName("USER").isEmpty())
            roleRepository.save(user_role) ;

        if(roleRepository.findByName("ADMIN").isEmpty())
            roleRepository.save(admin_role) ;
    }

     @Bean
    void createFirstAdmin(){
        Role admin_role = roleRepository.findByName("ADMIN").get() ;
        AppUser admin = AppUser.builder()
                .id(UUID.randomUUID())
                .firstName("ADMIN")
                .lastName("USER")
                .username("admin")
                .password(new BCryptPasswordEncoder().encode("admin"))
                .email("slahiddinebnj123@gmail.com")
                .roles(List.of(admin_role))
                .build() ;
        userRepository.save(admin) ;
     }

}
