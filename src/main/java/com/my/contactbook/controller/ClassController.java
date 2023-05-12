package com.my.contactbook.controller;

import com.my.contactbook.dto.ClassDTO;
import com.my.contactbook.dto.UserDTO;
import com.my.contactbook.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
        ClassDTO classDTO = classService.updateTeacherClass(dto);
        return new ResponseEntity<>(classDTO, HttpStatus.OK);
    }

    @PutMapping("/delete/{classId}")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity deleteClass(@PathVariable("classId") long classId) {
        classService.deleteClass(classId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<List<ClassDTO>> findClass() {
        List<ClassDTO> classDTO = classService.getAll();
        return new ResponseEntity<>(classDTO, HttpStatus.OK);
    }

    @GetMapping("/get-valid-teachers")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<List<UserDTO>> findValidTeachers() {
        return new ResponseEntity<>(classService.getValidTeachers(), HttpStatus.OK);
    }

    @GetMapping("/get-valid-students")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<List<UserDTO>> findValidStudents() {
        return new ResponseEntity<>(classService.getValidStudents(), HttpStatus.OK);
    }

    @PutMapping("/db-to-excel")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Resource> getStudentsToExcel(@RequestBody List<UserDTO> studentsList) {
        String filename = "students.xlsx";
        InputStreamResource file = new InputStreamResource(classService.getStudentsFromDb(studentsList));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }

    @GetMapping("/{id}")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<ClassDTO> findClass(@PathVariable("id") long classId) {
        ClassDTO classDTO = classService.findClass(classId);
        return new ResponseEntity<>(classDTO, HttpStatus.OK);
    }

    @GetMapping("/find-by/{name}")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<ClassDTO> findClassByName(@PathVariable("name") String name) {
        ClassDTO classDTO = classService.findClassByName(name);
        return new ResponseEntity<>(classDTO, HttpStatus.OK);
    }

    @GetMapping("/find-by-teacher/{code}")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<ClassDTO> findClassByTeacher(@PathVariable("code") String teacherCode) {
        ClassDTO classDTO = classService.getClassByTeacher(teacherCode);
        return new ResponseEntity<>(classDTO, HttpStatus.OK);
    }

    @PostMapping("/search")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<List<ClassDTO>> searchClass(@RequestBody String name) {
        List<ClassDTO> list = classService.searchClass(name);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
