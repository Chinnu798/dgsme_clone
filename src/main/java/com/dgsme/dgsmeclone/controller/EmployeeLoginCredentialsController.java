package com.dgsme.dgsmeclone.controller;

import com.dgsme.dgsmeclone.dto.EmployeeLoginCredentialsDto;
import com.dgsme.dgsmeclone.service.EmployeeLoginCredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee-login")
public class EmployeeLoginCredentialsController {

    @Autowired
    private EmployeeLoginCredentialsService service;

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateCredentials(@RequestBody EmployeeLoginCredentialsDto credentials) {
        boolean isValid = service.validateCredentials(credentials.getEmployeeEmail(), credentials.getEmployeePassword());
        return ResponseEntity.ok(isValid);
    }

    @PostMapping("/create")
    public ResponseEntity<EmployeeLoginCredentialsDto> createCredentials(@RequestBody EmployeeLoginCredentialsDto credentials) {
        EmployeeLoginCredentialsDto savedCredentials = service.save(credentials);
        return ResponseEntity.ok(savedCredentials);
    }
}
