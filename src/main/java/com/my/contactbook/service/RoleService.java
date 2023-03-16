package com.my.contactbook.service;

import com.my.contactbook.entity.RoleEntity;
import com.my.contactbook.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public RoleEntity setRoleData(String name, String prefix) {
        RoleEntity role = new RoleEntity();
        role.setRoleName(name);
        role.setRolePrefix(prefix);
        return role;
    }

    public void createRoles() {
        List<RoleEntity> rolesExist = roleRepository.findAll();
        if(rolesExist.isEmpty()){
            RoleEntity adminRole = setRoleData("ADMIN", "AD");
            RoleEntity formRole = setRoleData("MANAGER", "QL");
            RoleEntity subjRole = setRoleData("TEACHER", "GV");
            RoleEntity studentRole = setRoleData("STUDENT", "HS");
            List<RoleEntity> roles = new ArrayList<>();
            roles.add(adminRole);
            roles.add(formRole);
            roles.add(subjRole);
            roles.add(studentRole);

            roleRepository.saveAll(roles);
        }
    }
}
