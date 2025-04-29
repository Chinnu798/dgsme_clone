package com.dgsme.dgsmeclone.repository;

import com.dgsme.dgsmeclone.dto.AdminAddingEmpDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdminAddingEmpRepository extends JpaRepository<AdminAddingEmpDto, Long> {
    Optional<AdminAddingEmpDto> findByEmployeeEmail(String email);
    
    List<AdminAddingEmpDto> findByEmployeeDepartment(String department);
    
    List<AdminAddingEmpDto> findByEmployeePosition(String position);
    
    @Query("SELECT e FROM AdminAddingEmpDto e WHERE e.employeeName LIKE %:name%")
    List<AdminAddingEmpDto> findByEmployeeNameContaining(@Param("name") String name);
    
    @Query("SELECT e FROM AdminAddingEmpDto e WHERE e.joinDate BETWEEN :startDate AND :endDate")
    List<AdminAddingEmpDto> findByJoinDateBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
} 