package com.my.contactbook.controller;

import com.my.contactbook.dto.ScheduleDTO;
import com.my.contactbook.dto.SlotDTO;
import com.my.contactbook.dto.SubjectDTO;
import com.my.contactbook.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/schedules")
@CrossOrigin(origins = "*")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    @GetMapping("/all-slots")
    ResponseEntity<List<SlotDTO>> findAllSlot() {
        List<SlotDTO> list = scheduleService.getAllSlots();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/get-subjects/{className}")
    ResponseEntity<List<SubjectDTO>> findSubjectsByClassName(@PathVariable("className") String className) {
        List<SubjectDTO> list = scheduleService.getByClassName(className);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

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
    ResponseEntity<List<ScheduleDTO>> findAllSchedules() {
        List<ScheduleDTO> list = scheduleService.findAllSchedules();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/teacher/{userCode}")
    ResponseEntity<List<ScheduleDTO>> findAllSchedulesByTeacher(@PathVariable("userCode") String userCode) {
        List<ScheduleDTO> list = scheduleService.findSchedulesByTeacher(userCode);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/find-by-class/{className}")
    ResponseEntity<List<ScheduleDTO>> findAllSchedulesByClassName(@PathVariable("className") String className) {
        List<ScheduleDTO> list = scheduleService.findByClass(className);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/add-schedule-excel")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity addSchedulesExcel(@RequestParam("file") MultipartFile file) {
        scheduleService.addScheduleFromExcel(file);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
