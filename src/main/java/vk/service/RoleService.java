package vk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vk.domain.ERole;
import vk.domain.Role;
import vk.repos.RoleRepository;

import java.util.HashSet;
import java.util.Set;
@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;
    public RoleService(){}
    //переделать
    public Set<Role> validationRole(Set<String> reqRoles){
        Set<Role> roles = new HashSet<>();
        if (reqRoles == null) {
            Role userRole = roleRepository
                    .findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
            roles.add(userRole);
        } else {
            reqRoles.forEach(r -> {
                switch (r) {
                    case "admin":
                        Role adminRole = roleRepository
                                .findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error, Role ADMIN is not found"));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository
                                .findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error, Role MODERATOR is not found"));
                        roles.add(modRole);

                        break;

                    default:
                        Role userRole = roleRepository
                                .findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
                        roles.add(userRole);
                }
            });
        }
        return roles;
    }

    public void createRole(){
        if(!roleRepository.existsByName(ERole.ROLE_USER)){/// миграция
            roleRepository.save(new Role(ERole.ROLE_USER));
        }
        if(!roleRepository.existsByName(ERole.ROLE_ADMIN)){
            roleRepository.save(new Role(ERole.ROLE_ADMIN));
        }
        if(!roleRepository.existsByName(ERole.ROLE_MODERATOR)) {
            roleRepository.save(new Role(ERole.ROLE_MODERATOR));
        }
    }
}
