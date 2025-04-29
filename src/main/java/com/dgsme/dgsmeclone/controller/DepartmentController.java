package com.dgsme.dgsmeclone.controller;

import com.dgsme.dgsmeclone.dto.DepartmentDto;
import com.dgsme.dgsmeclone.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<DepartmentDto> addDepartment(@RequestBody DepartmentDto department) {
        DepartmentDto savedDepartment = departmentService.addDepartment(department);
        return new ResponseEntity<>(savedDepartment, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id)
                .map(department -> new ResponseEntity<>(department, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/employee/{empId}")
    public ResponseEntity<DepartmentDto> getDepartmentByEmpId(@PathVariable Long empId) {
        return departmentService.getDepartmentByEmpId(empId)
                .map(department -> new ResponseEntity<>(department, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        List<DepartmentDto> departments = departmentService.getAllDepartments();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @GetMapping("/dept/{deptName}")
    public ResponseEntity<List<DepartmentDto>> getDepartmentsByDeptName(@PathVariable String deptName) {
        List<DepartmentDto> departments = departmentService.getDepartmentsByDeptName(deptName);
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @GetMapping("/manager/{managerId}")
    public ResponseEntity<List<DepartmentDto>> getDepartmentsByManagerId(@PathVariable Long managerId) {
        List<DepartmentDto> departments = departmentService.getDepartmentsByManagerId(managerId);
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<DepartmentDto>> searchDepartmentsByEmpName(@RequestParam String name) {
        List<DepartmentDto> departments = departmentService.searchDepartmentsByEmpName(name);
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @GetMapping("/employee-manager")
    public ResponseEntity<DepartmentDto> getDepartmentByEmpIdAndManagerId(
            @RequestParam Long empId,
            @RequestParam Long managerId) {
        return departmentService.getDepartmentByEmpIdAndManagerId(empId, managerId)
                .map(department -> new ResponseEntity<>(department, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDto> updateDepartment(
            @PathVariable Long id,
            @RequestBody DepartmentDto updatedDepartment) {
        try {
            DepartmentDto department = departmentService.updateDepartment(id, updatedDepartment);
            return new ResponseEntity<>(department, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        try {
            departmentService.deleteDepartment(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
} 