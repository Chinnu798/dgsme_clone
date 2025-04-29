//package com.dgsme.dgsmeclone.service;
//
//import com.dgsme.dgsmeclone.dao.AdminAddingEmpDao;
//import com.dgsme.dgsmeclone.dto.AdminAddingEmpDto;
//import com.dgsme.dgsmeclone.repository.PunchInRepository;
//import com.dgsme.dgsmeclone.repository.PunchOutRepository;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class AdminAddingEmpService {
//
//	
//    private final AdminAddingEmpDao adminAddingEmpDao;
//
//    @Autowired
//    public AdminAddingEmpService(AdminAddingEmpDao adminAddingEmpDao) {
//        this.adminAddingEmpDao = adminAddingEmpDao;
//    }
//
//    public AdminAddingEmpDto addEmployee(AdminAddingEmpDto employee) {
//        if (employee.getJoinDate() == null) {
//            employee.setJoinDate(LocalDateTime.now());
//        }
//        return adminAddingEmpDao.saveEmployee(employee);
//    }
//
//    public Optional<AdminAddingEmpDto> getEmployeeById(Long id) {
//        return adminAddingEmpDao.getEmployeeById(id);
//    }
//
//    public Optional<AdminAddingEmpDto> getEmployeeByEmail(String email) {
//        return adminAddingEmpDao.getEmployeeByEmail(email);
//    }
//
//    public List<AdminAddingEmpDto> getAllEmployees() {
//        return adminAddingEmpDao.getAllEmployees();
//    }
//
//    public List<AdminAddingEmpDto> getEmployeesByDepartment(String department) {
//        return adminAddingEmpDao.getEmployeesByDepartment(department);
//    }
//
//    public List<AdminAddingEmpDto> getEmployeesByPosition(String position) {
//        return adminAddingEmpDao.getEmployeesByPosition(position);
//    }
//
//    public List<AdminAddingEmpDto> searchEmployeesByName(String name) {
//        return adminAddingEmpDao.searchEmployeesByName(name);
//    }
//
//    public List<AdminAddingEmpDto> getEmployeesByJoinDateRange(LocalDateTime startDate, LocalDateTime endDate) {
//        return adminAddingEmpDao.getEmployeesByJoinDateRange(startDate, endDate);
//    }
//
//    public AdminAddingEmpDto updateEmployee(Long id, AdminAddingEmpDto updatedEmployee) {
//        return adminAddingEmpDao.getEmployeeById(id)
//                .map(existingEmployee -> {
//                    existingEmployee.setEmployeeName(updatedEmployee.getEmployeeName());
//                    existingEmployee.setEmployeeEmail(updatedEmployee.getEmployeeEmail());
//                    existingEmployee.setEmployeePosition(updatedEmployee.getEmployeePosition());
//                    existingEmployee.setEmployeeDepartment(updatedEmployee.getEmployeeDepartment());
//                    existingEmployee.setEmployeePhone(updatedEmployee.getEmployeePhone());
//                    return adminAddingEmpDao.saveEmployee(existingEmployee);
//                })
//                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
//    }
//
//    public void deleteEmployee(Long id) {
//        adminAddingEmpDao.deleteEmployee(id);
//    }
//}























package com.dgsme.dgsmeclone.service;

import com.dgsme.dgsmeclone.dao.AdminAddingEmpDao;
import com.dgsme.dgsmeclone.dto.AdminAddingEmpDto;
import com.dgsme.dgsmeclone.dto.PunchInDto;
import com.dgsme.dgsmeclone.dto.PunchOutDto;
import com.dgsme.dgsmeclone.repository.PunchInRepository;
import com.dgsme.dgsmeclone.repository.PunchOutRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AdminAddingEmpService {

    private final AdminAddingEmpDao adminAddingEmpDao;
    private final PunchInRepository punchInRepository;
    private final PunchOutRepository punchOutRepository;

    @Autowired
    public AdminAddingEmpService(AdminAddingEmpDao adminAddingEmpDao, PunchInRepository punchInRepository, PunchOutRepository punchOutRepository) {
        this.adminAddingEmpDao = adminAddingEmpDao;
        this.punchInRepository = punchInRepository;
        this.punchOutRepository = punchOutRepository;
    }


  public AdminAddingEmpDto addEmployee(AdminAddingEmpDto employee) {
      if (employee.getJoinDate() == null) {
          employee.setJoinDate(LocalDateTime.now());
      }
      return adminAddingEmpDao.saveEmployee(employee);
  }

  public Optional<AdminAddingEmpDto> getEmployeeById(Long id) {
      return adminAddingEmpDao.getEmployeeById(id);
  }

  public Optional<AdminAddingEmpDto> getEmployeeByEmail(String email) {
      return adminAddingEmpDao.getEmployeeByEmail(email);
  }

  public List<AdminAddingEmpDto> getAllEmployees() {
      return adminAddingEmpDao.getAllEmployees();
  }

  public List<AdminAddingEmpDto> getEmployeesByDepartment(String department) {
      return adminAddingEmpDao.getEmployeesByDepartment(department);
  }

  public List<AdminAddingEmpDto> getEmployeesByPosition(String position) {
      return adminAddingEmpDao.getEmployeesByPosition(position);
  }

  public List<AdminAddingEmpDto> searchEmployeesByName(String name) {
      return adminAddingEmpDao.searchEmployeesByName(name);
  }

  public List<AdminAddingEmpDto> getEmployeesByJoinDateRange(LocalDateTime startDate, LocalDateTime endDate) {
      return adminAddingEmpDao.getEmployeesByJoinDateRange(startDate, endDate);
  }

  public AdminAddingEmpDto updateEmployee(Long id, AdminAddingEmpDto updatedEmployee) {
      return adminAddingEmpDao.getEmployeeById(id)
              .map(existingEmployee -> {
                  existingEmployee.setEmployeeName(updatedEmployee.getEmployeeName());
                  existingEmployee.setEmployeeEmail(updatedEmployee.getEmployeeEmail());
                  existingEmployee.setEmployeePosition(updatedEmployee.getEmployeePosition());
                  existingEmployee.setEmployeeDepartment(updatedEmployee.getEmployeeDepartment());
                  existingEmployee.setEmployeePhone(updatedEmployee.getEmployeePhone());
                  return adminAddingEmpDao.saveEmployee(existingEmployee);
              })
              .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
  }

  public void deleteEmployee(Long id) {
      adminAddingEmpDao.deleteEmployee(id);
  }
    // New method for getting all employee details including punch-in and punch-out info
    public List<Map<String, Object>> getAllEmployeeFullDetails() {
        List<AdminAddingEmpDto> employees = adminAddingEmpDao.getAllEmployees();
        List<Map<String, Object>> responseList = new ArrayList<>();

        for (AdminAddingEmpDto employee : employees) {
            Map<String, Object> employeeData = new HashMap<>();
            employeeData.put("employeeId", employee.getEmployeeId());
            employeeData.put("employeeName", employee.getEmployeeName());
            employeeData.put("employeeEmail", employee.getEmployeeEmail());
            employeeData.put("employeePhone", employee.getEmployeePhone());
            employeeData.put("employeePosition", employee.getEmployeePosition());
            employeeData.put("employeeDepartment", employee.getEmployeeDepartment());

            // Fetching punch-in and punch-out details
            PunchInDto punchIn = punchInRepository.findTopByEmployeeIdOrderByLoginDateDesc(employee.getEmployeeId());
            PunchOutDto punchOut = punchOutRepository.findTopByEmployeeIdOrderByLogoutDateDesc(employee.getEmployeeId());

            // Adding punch-in details to the map
            if (punchIn != null) {
                employeeData.put("loginDate", punchIn.getLoginDate());
                employeeData.put("loginTime", punchIn.getLoginTime());
                employeeData.put("loginLocation", punchIn.getLoginLocation());
            } else {
                employeeData.put("loginDate", null);
                employeeData.put("loginTime", null);
                employeeData.put("loginLocation", null);
            }

            // Adding punch-out details to the map
            if (punchOut != null) {
                employeeData.put("logoutDate", punchOut.getLogoutDate());
                employeeData.put("logoutTime", punchOut.getLogoutTime());
                employeeData.put("logoutLocation", punchOut.getLogoutLocation());
            } else {
                employeeData.put("logoutDate", null);
                employeeData.put("logoutTime", null);
                employeeData.put("logoutLocation", null);
            }

            responseList.add(employeeData);
        }

        return responseList;
    }
}
