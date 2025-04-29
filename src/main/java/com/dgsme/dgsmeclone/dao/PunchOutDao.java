package com.dgsme.dgsmeclone.dao;

import com.dgsme.dgsmeclone.dto.PunchOutDto;
import com.dgsme.dgsmeclone.repository.PunchOutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Component
public class PunchOutDao {
    
    private final PunchOutRepository repository;

    @Autowired
    public PunchOutDao(PunchOutRepository repository) {
        this.repository = repository;
    }

    public PunchOutDto savePunchOut(PunchOutDto punchOut) {
        // Check if employee has already punched out today
        if (repository.existsByEmployeeIdAndLogoutDate(punchOut.getEmployeeId(), LocalDate.now())) {
            throw new IllegalStateException("Employee has already punched out today");
        }

        // Validate punch-out time is within business hours (9 AM to 6 PM)
        LocalTime currentTime = LocalTime.now();
        if (currentTime.isBefore(LocalTime.of(9, 0)) || currentTime.isAfter(LocalTime.of(18, 0))) {
            throw new IllegalStateException("Punch-out is only allowed between 9 AM and 6 PM");
        }
        
        return repository.save(punchOut);
    }

    public List<PunchOutDto> getPunchOutsByEmployeeAndDate(Long employeeId, LocalDate date) {
        return repository.findByEmployeeIdAndLogoutDate(employeeId, date);
    }

    public Optional<PunchOutDto> getLastPunchOut(Long employeeId) {
        List<PunchOutDto> punchOuts = repository.findByEmployeeIdAndLogoutDate(employeeId, LocalDate.now());
        return punchOuts.stream()
            .max((p1, p2) -> p1.getLogoutTime().compareTo(p2.getLogoutTime()));
    }

    public List<PunchOutDto> getPunchOutsByDateRange(Long employeeId, LocalDate startDate, LocalDate endDate) {
        return repository.findByEmployeeIdAndLogoutDateBetween(employeeId, startDate, endDate);
    }

    public boolean hasPunchedOutToday(Long employeeId) {
        return repository.existsByEmployeeIdAndLogoutDate(employeeId, LocalDate.now());
    }

    public List<PunchOutDto> getEarlyPunchOuts(LocalDate date) {
        return repository.findEarlyPunchOuts(date, LocalTime.of(17, 0));
    }

    public List<PunchOutDto> getLatePunchOuts(LocalDate date) {
        return repository.findLatePunchOuts(date, LocalTime.of(18, 0));
    }

    public List<PunchOutDto> getPunchOutsByTimeRange(Long employeeId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        return repository.findByEmployeeIdAndLogoutDateAndLogoutTimeBetween(employeeId, date, startTime, endTime);
    }
} 