package com.dgsme.dgsmeclone.dao;

import com.dgsme.dgsmeclone.dto.AdminAddingEmpDto;
import com.dgsme.dgsmeclone.repository.AdminAddingEmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class AdminAddingEmpDao {

    private final AdminAddingEmpRepository repository;

    @Autowired
    public AdminAddingEmpDao(AdminAddingEmpRepository repository) {
        this.repository = repository;
    }

    public AdminAddingEmpDto saveEmployee(AdminAddingEmpDto employee) {
        validateEmployee(employee);
        return repository.save(employee);
    }

    public Optional<AdminAddingEmpDto> getEmployeeById(Long id) {
        return repository.findById(id);
    }

    public Optional<AdminAddingEmpDto> getEmployeeByEmail(String email) {
        return repository.findByEmployeeEmail(email);
    }

    public List<AdminAddingEmpDto> getAllEmployees() {
        return repository.findAll();
    }

    public List<AdminAddingEmpDto> getEmployeesByDepartment(String department) {
        return repository.findByEmployeeDepartment(department);
    }

    public List<AdminAddingEmpDto> getEmployeesByPosition(String position) {
        return repository.findByEmployeePosition(position);
    }

    public List<AdminAddingEmpDto> searchEmployeesByName(String name) {
        return repository.findByEmployeeNameContaining(name);
    }

    public List<AdminAddingEmpDto> getEmployeesByJoinDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return repository.findByJoinDateBetween(startDate, endDate);
    }

    public void deleteEmployee(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Employee not found with id: " + id);
        }
        repository.deleteById(id);
    }

    private void validateEmployee(AdminAddingEmpDto employee) {
        if (employee.getEmployeeName() == null || employee.getEmployeeName().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be empty");
        }
        if (employee.getEmployeeEmail() == null || employee.getEmployeeEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee email cannot be empty");
        }
        if (employee.getEmployeePosition() == null || employee.getEmployeePosition().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee position cannot be empty");
        }
        if (employee.getEmployeeDepartment() == null || employee.getEmployeeDepartment().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee department cannot be empty");
        }
        if (employee.getEmployeePhone() == null || employee.getEmployeePhone().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee phone cannot be empty");
        }
    }
}
