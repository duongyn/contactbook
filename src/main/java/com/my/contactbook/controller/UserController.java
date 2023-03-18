package com.my.contactbook.controller;

import com.my.contactbook.dto.UserDTO;
import com.my.contactbook.dto.UserEditDTO;
import com.my.contactbook.service.RoleService;
import com.my.contactbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @EventListener(ApplicationReadyEvent.class)
    public void createRolesForDb() {
        roleService.createRoles();
    }

    @GetMapping("/roles")
    ResponseEntity createRoles() {
        roleService.createRoles();
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("")
        @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO user) {
        UserDTO dto = userService.createUser(user);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping("/update")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserEditDTO user) {
        UserDTO dto = userService.updateUser(user);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/delete/{userCode}")
        @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    ResponseEntity deleteUser(@PathVariable("userCode") String userCode) {
        userService.deleteUser(userCode);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("")
    ResponseEntity<List<UserDTO>> getAllActiveUsers() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/get/{username}")
    ResponseEntity<UserDTO> getUserByUsername(@PathVariable("username") String username) {
        return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);
    }


}
