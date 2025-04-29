//package com.dgsme.dgsmeclone.repository;
//
//import com.dgsme.dgsmeclone.dto.EmployeeLoginCredentialsDto;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//public interface EmployeeLoginCredentialsRepository extends JpaRepository<EmployeeLoginCredentialsDto, Integer> {
//    Optional<EmployeeLoginCredentialsDto> findByEmployeeEmail(String employeeEmail);
//}













package com.dgsme.dgsmeclone.repository;

import com.dgsme.dgsmeclone.dto.EmployeeLoginCredentialsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmployeeLoginCredentialsRepository extends JpaRepository<EmployeeLoginCredentialsDto, Integer> {
    Optional<EmployeeLoginCredentialsDto> findByEmployeeEmail(String employeeEmail);
}
