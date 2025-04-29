package com.dgsme.dgsmeclone.repository;

import com.dgsme.dgsmeclone.dto.PunchOutDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface PunchOutRepository extends JpaRepository<PunchOutDto, Long> {
    List<PunchOutDto> findByEmployeeIdAndLogoutDate(Long employeeId, LocalDate date);
    
    PunchOutDto findTopByEmployeeIdOrderByLogoutDateDesc(int employeeId);
    
    List<PunchOutDto> findByLogoutDate(LocalDate date);
    
    @Query("SELECT p FROM PunchOutDto p WHERE p.employeeId = :employeeId AND p.logoutDate BETWEEN :startDate AND :endDate")
    List<PunchOutDto> findByEmployeeIdAndLogoutDateBetween(
            @Param("employeeId") Long employeeId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
    
    @Query("SELECT p FROM PunchOutDto p WHERE p.logoutDate = :date AND p.logoutTime < :time")
    List<PunchOutDto> findEarlyPunchOuts(
            @Param("date") LocalDate date,
            @Param("time") LocalTime time);
    
    @Query("SELECT p FROM PunchOutDto p WHERE p.logoutDate = :date AND p.logoutTime > :time")
    List<PunchOutDto> findLatePunchOuts(
            @Param("date") LocalDate date,
            @Param("time") LocalTime time);
    
    @Query("SELECT p FROM PunchOutDto p WHERE p.employeeId = :employeeId AND p.logoutDate = :date AND p.logoutTime BETWEEN :startTime AND :endTime")
    List<PunchOutDto> findByEmployeeIdAndLogoutDateAndLogoutTimeBetween(
            @Param("employeeId") Long employeeId,
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime);
    
    @Query("SELECT COUNT(p) > 0 FROM PunchOutDto p WHERE p.employeeId = :employeeId AND p.logoutDate = :date")
    boolean existsByEmployeeIdAndLogoutDate(
            @Param("employeeId") Long employeeId,
            @Param("date") LocalDate date);
} 