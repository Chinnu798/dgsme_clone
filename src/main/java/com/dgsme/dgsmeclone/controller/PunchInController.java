package com.dgsme.dgsmeclone.controller;

import com.dgsme.dgsmeclone.dto.PunchInDto;
import com.dgsme.dgsmeclone.service.PunchInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/punch-in")
public class PunchInController {
    
    private final PunchInService service;

    @Autowired
    public PunchInController(PunchInService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> punchIn(@RequestBody PunchInDto punchIn) {
        try {
            PunchInDto savedPunchIn = service.recordPunchIn(punchIn);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPunchIn);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getPunchIns(
            @PathVariable Long employeeId,
            @RequestParam(required = false) LocalDate date) {
        try {
            if (date == null) {
                date = LocalDate.now();
            }
            List<PunchInDto> punchIns = service.getEmployeePunchIns(employeeId, date);
            return ResponseEntity.ok(punchIns);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{employeeId}/last")
    public ResponseEntity<?> getLastPunchIn(@PathVariable Long employeeId) {
        try {
            Optional<PunchInDto> lastPunchIn = service.getLastPunchIn(employeeId);
            return lastPunchIn
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{employeeId}/range")
    public ResponseEntity<?> getPunchInsByDateRange(
            @PathVariable Long employeeId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        try {
            List<PunchInDto> punchIns = service.getPunchInsByDateRange(employeeId, startDate, endDate);
            return ResponseEntity.ok(punchIns);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
} 