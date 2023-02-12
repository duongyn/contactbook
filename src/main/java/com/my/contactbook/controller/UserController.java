package com.my.contactbook.controller;

import com.my.contactbook.dto.UserDTO;
import com.my.contactbook.dto.UserEditDTO;
import com.my.contactbook.service.RoleService;
import com.my.contactbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

	@GetMapping("/roles")
	ResponseEntity createRoles() {
		roleService.createRoles();
		return new ResponseEntity(HttpStatus.OK);
	}

	@PostMapping("")
	//@PreAuthorize("hasAuthority('ADMIN')")
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

//	@PutMapping("")
//	@PreAuthorize("hasAuthority('ADMIN')")
//	ResponseEntity<UserEditDTO> editUser(@Valid @RequestBody UserEditDTO user) {
//		UserEditDTO dto = userService.editUser(user);
//		return new ResponseEntity<>(dto, HttpStatus.OK);
//	}

	@GetMapping("")
	ResponseEntity<List<UserDTO>> getAllActiveUsers() {
		return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
	}

//	@GetMapping("/search/{searchKey}")
//	ResponseEntity<List<UserDTO>> getUsersBySearch(@PathVariable("searchKey") String searchKey) {
//		return new ResponseEntity<>(userService.getUsersBySearch(searchKey), HttpStatus.OK);
//	}

}
