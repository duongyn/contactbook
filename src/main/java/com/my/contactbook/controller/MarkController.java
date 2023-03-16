package com.my.contactbook.controller;

import com.my.contactbook.dto.ClassDTO;
import com.my.contactbook.dto.MarkDTO;
import com.my.contactbook.service.ClassService;
import com.my.contactbook.service.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/marks")
@CrossOrigin(origins = "*")
public class MarkController {
    @Autowired
    MarkService markService;

    @PostMapping("")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<MarkDTO> createMark(@Valid @RequestBody MarkDTO dto) {
        MarkDTO markDTO = markService.createMark(dto);
        return new ResponseEntity<>(markDTO, HttpStatus.CREATED);
    }

    @GetMapping("/by-student/{code}")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<List<MarkDTO>> findMarksByStudent(@PathVariable("code") String studentCode) {
        List<MarkDTO> classDTO = markService.findMarksByStudent(studentCode);
        return new ResponseEntity<>(classDTO, HttpStatus.OK);
    }
}
