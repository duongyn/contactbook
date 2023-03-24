package com.my.contactbook.controller;

import com.my.contactbook.dto.ScheduleDTO;
import com.my.contactbook.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @PutMapping("")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<ScheduleDTO> updateSchedule(@Valid @RequestBody ScheduleDTO dto) {
        ScheduleDTO schedule = scheduleService.updateSchedule(dto);
        return new ResponseEntity<>(schedule, HttpStatus.CREATED);
    }

    @PutMapping("/delete/{scheduleId}")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity deleteSchedule(@PathVariable("scheduleId") long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<ScheduleDTO> findSchedule(@PathVariable("id") long scheduleId) {
        ScheduleDTO dto = scheduleService.findSchedule(scheduleId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<List<ScheduleDTO>> findAllSchedules() {
        List<ScheduleDTO> list = scheduleService.findAllSchedules();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
