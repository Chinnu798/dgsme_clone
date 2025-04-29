package com.dgsme.dgsmeclone.service;

import com.dgsme.dgsmeclone.dao.DepartmentDao;
import com.dgsme.dgsmeclone.dto.DepartmentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    private final DepartmentDao departmentDao;

    @Autowired
    public DepartmentService(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    public DepartmentDto addDepartment(DepartmentDto department) {
        return departmentDao.saveDepartment(department);
    }

    public Optional<DepartmentDto> getDepartmentById(Long id) {
        return departmentDao.getDepartmentById(id);
    }

    public Optional<DepartmentDto> getDepartmentByEmpId(Long empId) {
        return departmentDao.getDepartmentByEmpId(empId);
    }

    public List<DepartmentDto> getAllDepartments() {
        return departmentDao.getAllDepartments();
    }

    public List<DepartmentDto> getDepartmentsByDeptName(String deptName) {
        return departmentDao.getDepartmentsByDeptName(deptName);
    }

    public List<DepartmentDto> getDepartmentsByManagerId(Long managerId) {
        return departmentDao.getDepartmentsByManagerId(managerId);
    }

    public List<DepartmentDto> searchDepartmentsByEmpName(String name) {
        return departmentDao.searchDepartmentsByEmpName(name);
    }

    public Optional<DepartmentDto> getDepartmentByEmpIdAndManagerId(Long empId, Long managerId) {
        return departmentDao.getDepartmentByEmpIdAndManagerId(empId, managerId);
    }

    public DepartmentDto updateDepartment(Long id, DepartmentDto updatedDepartment) {
        return departmentDao.getDepartmentById(id)
                .map(existingDepartment -> {
                    existingDepartment.setEmpId(updatedDepartment.getEmpId());
                    existingDepartment.setEmpName(updatedDepartment.getEmpName());
                    existingDepartment.setEmpDept(updatedDepartment.getEmpDept());
                    existingDepartment.setManagerId(updatedDepartment.getManagerId());
                    return departmentDao.saveDepartment(existingDepartment);
                })
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
    }

    public void deleteDepartment(Long id) {
        departmentDao.deleteDepartment(id);
    }
} 