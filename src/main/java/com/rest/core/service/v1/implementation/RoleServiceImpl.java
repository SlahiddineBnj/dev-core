package com.rest.core.service.v1.implementation;

import com.rest.core.model.Role;
import com.rest.core.repository.RoleRepository;
import com.rest.core.service.v1.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository ;

    @Override
    public Role getRoleByName(String roleName) {
        return roleRepository.findByName(roleName).get();
    }
}
