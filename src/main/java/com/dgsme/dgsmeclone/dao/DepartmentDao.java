package com.dgsme.dgsmeclone.dao;

import com.dgsme.dgsmeclone.dto.DepartmentDto;
import com.dgsme.dgsmeclone.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DepartmentDao {
    private final DepartmentRepository repository;

    @Autowired
    public DepartmentDao(DepartmentRepository repository) {
        this.repository = repository;
    }

    public DepartmentDto saveDepartment(DepartmentDto department) {
        validateDepartment(department);
        return repository.save(department);
    }

    public Optional<DepartmentDto> getDepartmentById(Long id) {
        return repository.findById(id);
    }

    public Optional<DepartmentDto> getDepartmentByEmpId(Long empId) {
        return repository.findByEmpId(empId);
    }

    public List<DepartmentDto> getDepartmentsByDeptName(String deptName) {
        return repository.findByEmpDept(deptName);
    }

    public List<DepartmentDto> getDepartmentsByManagerId(Long managerId) {
        return repository.findByManagerId(managerId);
    }

    public List<DepartmentDto> searchDepartmentsByEmpName(String name) {
        return repository.findByEmpNameContaining(name);
    }

    public Optional<DepartmentDto> getDepartmentByEmpIdAndManagerId(Long empId, Long managerId) {
        return repository.findByEmpIdAndManagerId(empId, managerId);
    }

    public List<DepartmentDto> getAllDepartments() {
        return repository.findAll();
    }

    public void deleteDepartment(Long id) {
        repository.deleteById(id);
    }

    private void validateDepartment(DepartmentDto department) {
        if (department.getEmpId() == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        if (department.getEmpName() == null || department.getEmpName().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be empty");
        }
        if (department.getEmpDept() == null || department.getEmpDept().trim().isEmpty()) {
            throw new IllegalArgumentException("Department name cannot be empty");
        }
        if (department.getManagerId() == null) {
            throw new IllegalArgumentException("Manager ID cannot be null");
        }
    }
} 