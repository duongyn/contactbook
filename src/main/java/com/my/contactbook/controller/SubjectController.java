package com.my.contactbook.controller;


import com.my.contactbook.dto.SubjectDTO;
import com.my.contactbook.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/subjects")
@CrossOrigin(origins = "*")
public class SubjectController {
    @Autowired
    SubjectService subjectService;

    @PostMapping("")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<SubjectDTO> createSubject(@Valid @RequestBody SubjectDTO subject) {
        SubjectDTO dto = subjectService.createSubject(subject);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
