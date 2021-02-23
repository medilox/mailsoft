package com.mailsoft;

import com.mailsoft.domain.Role;
import com.mailsoft.repository.RoleRepository;
import com.mailsoft.service.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * This dataset prepares initial data and persist them into database before application is launched
 */

@Component
public class InitialDataSet {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    public InitializingBean load() {
        createRoles();
        createDefaultUser();
        return null;
    }

    private void createRoles() {
        List<String> roles = Arrays.asList("ADMIN", "OPERATOR");
        for(String role : roles){
            if(roleRepository.findByName(role) == null){
                roleRepository.save(new Role(role));
            }
        }
    }

    private void createDefaultUser() {
        if(userService.findByLogin("admin") == null)
            userService.addNewUser("admin", "ADMIN");
    }

}
