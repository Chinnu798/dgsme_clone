package com.dgsme.dgsmeclone.controller;

import com.dgsme.dgsmeclone.dto.PunchOutDto;
import com.dgsme.dgsmeclone.service.PunchOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/punch-out")
public class PunchOutController {
    
    private final PunchOutService service;

    @Autowired
    public PunchOutController(PunchOutService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> punchOut(@RequestBody PunchOutDto punchOut) {
        try {
            PunchOutDto savedPunchOut = service.recordPunchOut(punchOut);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPunchOut);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getPunchOuts(
            @PathVariable Long employeeId,
            @RequestParam(required = false) LocalDate date) {
        try {
            if (date == null) {
                date = LocalDate.now();
            }
            List<PunchOutDto> punchOuts = service.getEmployeePunchOuts(employeeId, date);
            return ResponseEntity.ok(punchOuts);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{employeeId}/last")
    public ResponseEntity<?> getLastPunchOut(@PathVariable Long employeeId) {
        try {
            Optional<PunchOutDto> lastPunchOut = service.getLastPunchOut(employeeId);
            return lastPunchOut
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{employeeId}/range")
    public ResponseEntity<?> getPunchOutsByDateRange(
            @PathVariable Long employeeId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        try {
            List<PunchOutDto> punchOuts = service.getPunchOutsByDateRange(employeeId, startDate, endDate);
            return ResponseEntity.ok(punchOuts);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/early/{date}")
    public ResponseEntity<?> getEarlyPunchOuts(@PathVariable LocalDate date) {
        try {
            List<PunchOutDto> earlyPunchOuts = service.getEarlyPunchOuts(date);
            return ResponseEntity.ok(earlyPunchOuts);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/late/{date}")
    public ResponseEntity<?> getLatePunchOuts(@PathVariable LocalDate date) {
        try {
            List<PunchOutDto> latePunchOuts = service.getLatePunchOuts(date);
            return ResponseEntity.ok(latePunchOuts);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{employeeId}/time-range")
    public ResponseEntity<?> getPunchOutsByTimeRange(
            @PathVariable Long employeeId,
            @RequestParam LocalDate date,
            @RequestParam LocalTime startTime,
            @RequestParam LocalTime endTime) {
        try {
            List<PunchOutDto> punchOuts = service.getPunchOutsByTimeRange(employeeId, date, startTime, endTime);
            return ResponseEntity.ok(punchOuts);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
} 
 