package com.my.contactbook.controller;


import com.my.contactbook.dto.SubjectDTO;
import com.my.contactbook.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @PutMapping("")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<SubjectDTO> updateSubject(@Valid @RequestBody SubjectDTO subject) {
        SubjectDTO dto = subjectService.editSubject(subject);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/delete/{subjectId}")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity deleteSubject(@PathVariable("subjectId") long subjectId) {
        subjectService.deleteSubject(subjectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<List<SubjectDTO>> getAll() {
        List<SubjectDTO> dto = subjectService.getAll();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<SubjectDTO> getById(@PathVariable("id") long id) {
        SubjectDTO dto = subjectService.getById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
