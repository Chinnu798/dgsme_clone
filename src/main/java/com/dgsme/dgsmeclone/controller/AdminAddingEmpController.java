package com.dgsme.dgsmeclone.controller;

import com.dgsme.dgsmeclone.dto.AdminAddingEmpDto;
import com.dgsme.dgsmeclone.service.AdminAddingEmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin/employees")
public class AdminAddingEmpController {

    private final AdminAddingEmpService adminAddingEmpService;

    @Autowired
    public AdminAddingEmpController(AdminAddingEmpService adminAddingEmpService) {
        this.adminAddingEmpService = adminAddingEmpService;
    }

    @PostMapping
    public ResponseEntity<AdminAddingEmpDto> addEmployee(@RequestBody AdminAddingEmpDto employee) {
        AdminAddingEmpDto savedEmployee = adminAddingEmpService.addEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminAddingEmpDto> getEmployeeById(@PathVariable Long id) {
        return adminAddingEmpService.getEmployeeById(id)
                .map(employee -> new ResponseEntity<>(employee, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<AdminAddingEmpDto> getEmployeeByEmail(@PathVariable String email) {
        return adminAddingEmpService.getEmployeeByEmail(email)
                .map(employee -> new ResponseEntity<>(employee, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<AdminAddingEmpDto>> getAllEmployees() {
        List<AdminAddingEmpDto> employees = adminAddingEmpService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<AdminAddingEmpDto>> getEmployeesByDepartment(@PathVariable String department) {
        List<AdminAddingEmpDto> employees = adminAddingEmpService.getEmployeesByDepartment(department);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/position/{position}")
    public ResponseEntity<List<AdminAddingEmpDto>> getEmployeesByPosition(@PathVariable String position) {
        List<AdminAddingEmpDto> employees = adminAddingEmpService.getEmployeesByPosition(position);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AdminAddingEmpDto>> searchEmployeesByName(@RequestParam String name) {
        List<AdminAddingEmpDto> employees = adminAddingEmpService.searchEmployeesByName(name);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/join-date-range")
    public ResponseEntity<List<AdminAddingEmpDto>> getEmployeesByJoinDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        List<AdminAddingEmpDto> employees = adminAddingEmpService.getEmployeesByJoinDateRange(startDate, endDate);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
    
    
    
    
    
    
    @GetMapping("/full-details")
    public ResponseEntity<List<Map<String, Object>>> getAllEmployeeFullDetails() {
        List<Map<String, Object>> employeeFullDetails = adminAddingEmpService.getAllEmployeeFullDetails();
        return new ResponseEntity<>(employeeFullDetails, HttpStatus.OK);
    }
    
    
    
    
    

    @PutMapping("/{id}")
    public ResponseEntity<AdminAddingEmpDto> updateEmployee(
            @PathVariable Long id,
            @RequestBody AdminAddingEmpDto updatedEmployee) {
        try {
            AdminAddingEmpDto employee = adminAddingEmpService.updateEmployee(id, updatedEmployee);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } 
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        try {
            adminAddingEmpService.deleteEmployee(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
