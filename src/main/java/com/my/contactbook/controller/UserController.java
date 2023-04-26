package com.my.contactbook.controller;

import com.my.contactbook.dto.UserDTO;
import com.my.contactbook.dto.UserEditDTO;
import com.my.contactbook.entity.RoleEntity;
import com.my.contactbook.entity.UserEntity;
import com.my.contactbook.service.RoleService;
import com.my.contactbook.service.ScheduleService;
import com.my.contactbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    ScheduleService scheduleService;

    @EventListener(ApplicationReadyEvent.class)
    public void createDefaultDataForDb() {
        roleService.createRoles();
        userService.createDefaultAdmin();
        scheduleService.createDefaultSlots();
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

    @GetMapping("/by-class/{className}")
    ResponseEntity<List<UserDTO>> getAllActiveUsersByClass(@PathVariable("className") String className) {
        return new ResponseEntity<>(userService.getUsersByClass(className), HttpStatus.OK);
    }

    @GetMapping("/get/{username}")
    ResponseEntity<UserDTO> getUserByUsername(@PathVariable("username") String username) {
        return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<UserDTO> getUserByCode(@PathVariable("id") String userCode) {
        return new ResponseEntity<>(userService.getUserByCode(userCode), HttpStatus.OK);
    }

    @PostMapping("/add-user-excel")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity addUsersExcel(@RequestParam("file") MultipartFile file) {
        userService.addUserFromExcel(file);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
