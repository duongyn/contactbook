package com.my.contactbook.controller;

import com.my.contactbook.dto.ClassDTO;
import com.my.contactbook.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

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

    @PutMapping("/add-student")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<ClassDTO> updateStudentsClass(@Valid @RequestBody ClassDTO dto) {
        ClassDTO classDTO = classService.updateStudentsClass(dto.getId(), dto.getListStudentCode());
        return new ResponseEntity<>(classDTO, HttpStatus.OK);
    }

    @PutMapping("/add-student-excel/{classId}")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity updateStudentsExcel(@PathVariable("classId") long classId, @RequestParam("file") MultipartFile file) {
        classService.addStudentFromExcel(classId, file);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/add-teacher")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<ClassDTO> updateTeacherClass(@Valid @RequestBody ClassDTO dto) {
        ClassDTO classDTO = classService.updateTeacherClass(dto.getId(), dto.getFormTeacherCode());
        return new ResponseEntity<>(classDTO, HttpStatus.OK);
    }

    @GetMapping("")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<List<ClassDTO>> findClass() {
        List<ClassDTO> classDTO = classService.getAll();
        return new ResponseEntity<>(classDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<ClassDTO> findClass(@PathVariable("id") long classId) {
        ClassDTO classDTO = classService.findClass(classId);
        return new ResponseEntity<>(classDTO, HttpStatus.OK);
    }

}
