package com.my.contactbook.controller;

import com.my.contactbook.dto.AttendanceDTO;
import com.my.contactbook.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/attendances")
@CrossOrigin(origins = "*")
public class AttendanceController {

    @Autowired
    AttendanceService attendanceService;

    @PostMapping("")
    ResponseEntity<AttendanceDTO> createAttendance(@Valid @RequestBody AttendanceDTO dto) {
        return new ResponseEntity<>(attendanceService.createAttendance(dto), HttpStatus.CREATED);
    }

    @PutMapping("")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<AttendanceDTO> updateUser(@Valid @RequestBody AttendanceDTO dto) {
        return new ResponseEntity<>(attendanceService.updateAttendance(dto), HttpStatus.OK);
    }

    @GetMapping("")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<List<AttendanceDTO>> findAll() {
        List<AttendanceDTO> list = attendanceService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/check-attend")
    ResponseEntity checkUserAttend(@RequestBody AttendanceDTO dtp) {
        return new ResponseEntity<>(attendanceService.checkUserAttend(dtp.getUserCode(), dtp.getScheduleId()), HttpStatus.OK);
    }

    @GetMapping("/check-attend/{userCode}/{scheduleId}")
    ResponseEntity checkUserAttendBasic(@PathVariable("userCode") String userCode, @PathVariable("scheduleId") long scheduleId) {
        return new ResponseEntity<>(attendanceService.checkUserAttend(userCode, scheduleId), HttpStatus.OK);
    }

    @GetMapping("/get-by/{userCode}")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<List<AttendanceDTO>> findByUser(@PathVariable("userCode") String userCode) {
        List<AttendanceDTO> list = attendanceService.findByUser(userCode);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
