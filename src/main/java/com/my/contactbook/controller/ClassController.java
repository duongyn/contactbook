package com.my.contactbook.controller;

import com.my.contactbook.dto.ClassDTO;
import com.my.contactbook.dto.UserDTO;
import com.my.contactbook.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/classes")
@CrossOrigin(origins = "*")
public class ClassController {
    @Autowired
    ClassService classService;

    @PostMapping("")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<ClassDTO> createClass(@Valid @RequestBody ClassDTO dto) {
        ClassDTO classDTO = classService.createClass(dto);
        return new ResponseEntity<>(classDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<ClassDTO> findClass(@PathVariable("id") long classId) {
        ClassDTO classDTO = classService.findClass(classId);
        return new ResponseEntity<>(classDTO, HttpStatus.OK);
    }

}
