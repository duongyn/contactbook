package com.my.contactbook.controller;

import com.my.contactbook.dto.ScheduleDTO;
import com.my.contactbook.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/schedules")
@CrossOrigin(origins = "*")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    @PostMapping("")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<ScheduleDTO> createSchedule(@Valid @RequestBody ScheduleDTO dto) {
        ScheduleDTO schedule = scheduleService.createSchedule(dto);
        return new ResponseEntity<>(schedule, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<ScheduleDTO> findClass(@PathVariable("id") long scheduleId) {
        ScheduleDTO dto = scheduleService.findSchedule(scheduleId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
