package com.my.contactbook.controller;

import com.my.contactbook.dto.LessonDTO;
import com.my.contactbook.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/lessons")
@CrossOrigin(origins = "*")
public class LessonController {
    @Autowired
    LessonService lessonService;

//    @PostMapping("")
//        //@PreAuthorize("hasAuthority('ADMIN')")
//    ResponseEntity<LessonDTO> createLesson(@Valid @RequestBody LessonDTO dto) {
//        LessonDTO lessonDTO = lessonService.createLesson(dto);
//        return new ResponseEntity<>(lessonDTO, HttpStatus.CREATED);
//    }
//
//    @GetMapping("/subject/{subjectId}")
//        //@PreAuthorize("hasAuthority('ADMIN')")
//    ResponseEntity<List<LessonDTO>> findLessonsBySubject(@PathVariable("subjectId") long subjectId) {
//        List<LessonDTO> list = lessonService.findBySubjectId(subjectId);
//        return new ResponseEntity<>(list, HttpStatus.OK);
//    }
}
