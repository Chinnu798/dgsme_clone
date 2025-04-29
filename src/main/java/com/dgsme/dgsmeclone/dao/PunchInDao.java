package com.dgsme.dgsmeclone.dao;

import com.dgsme.dgsmeclone.dto.PunchInDto;
import com.dgsme.dgsmeclone.repository.PunchInRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Component
public class PunchInDao {
    
    private final PunchInRepository repository;

    @Autowired
    public PunchInDao(PunchInRepository repository) {
        this.repository = repository;
    }

    public PunchInDto savePunchIn(PunchInDto punchIn) {
        // Check if employee has already punched in today
        if (repository.existsByEmployeeIdAndLoginDate(punchIn.getEmployeeId(), LocalDate.now())) {
            throw new IllegalStateException("Employee has already punched in today");
        }

        // Validate punch-in time is within business hours (9 AM to 6 PM)
        LocalTime currentTime = LocalTime.now();
        if (currentTime.isBefore(LocalTime.of(9, 0)) || currentTime.isAfter(LocalTime.of(18, 0))) {
            throw new IllegalStateException("Punch-in is only allowed between 9 AM and 6 PM");
        }
        
        return repository.save(punchIn);
    }

    public List<PunchInDto> getPunchInsByEmployeeAndDate(Long employeeId, LocalDate date) {
        return repository.findByEmployeeIdAndLoginDate(employeeId, date);
    }

    public Optional<PunchInDto> getLastPunchIn(Long employeeId) {
        List<PunchInDto> punchIns = repository.findByEmployeeIdAndLoginDate(employeeId, LocalDate.now());
        return punchIns.stream()
            .max((p1, p2) -> p1.getLoginTime().compareTo(p2.getLoginTime()));
    }

    public List<PunchInDto> getPunchInsByDateRange(Long employeeId, LocalDate startDate, LocalDate endDate) {
        return repository.findByEmployeeIdAndLoginDateBetween(employeeId, startDate, endDate);
    }

    public boolean hasPunchedInToday(Long employeeId) {
        return repository.existsByEmployeeIdAndLoginDate(employeeId, LocalDate.now());
    }

    public List<PunchInDto> getLatePunchIns(LocalDate date) {
        return repository.findLatePunchIns(date, LocalTime.of(9, 15));
    }

    public List<PunchInDto> getEarlyPunchIns(LocalDate date) {
        return repository.findEarlyPunchIns(date, LocalTime.of(9, 0));
    }

    public List<PunchInDto> getPunchInsByTimeRange(Long employeeId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        return repository.findByEmployeeIdAndLoginDateAndLoginTimeBetween(employeeId, date, startTime, endTime);
    }
} 