package com.dgsme.dgsmeclone.service;

import com.dgsme.dgsmeclone.dao.PunchInDao;
import com.dgsme.dgsmeclone.dto.PunchInDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class PunchInService {
    
    private final PunchInDao dao;

    @Autowired
    public PunchInService(PunchInDao dao) {
        this.dao = dao;
    }

    public PunchInDto recordPunchIn(PunchInDto punchIn) {
        validatePunchIn(punchIn);
        
        punchIn.setLoginDate(LocalDate.now());
        punchIn.setLoginTime(LocalTime.now());
        
        return dao.savePunchIn(punchIn);
    }

    public List<PunchInDto> getEmployeePunchIns(Long employeeId, LocalDate date) {
        return dao.getPunchInsByEmployeeAndDate(employeeId, date);
    }

    public Optional<PunchInDto> getLastPunchIn(Long employeeId) {
        return dao.getLastPunchIn(employeeId);
    }

    public List<PunchInDto> getPunchInsByDateRange(Long employeeId, LocalDate startDate, LocalDate endDate) {
        validateDateRange(startDate, endDate);
        return dao.getPunchInsByDateRange(employeeId, startDate, endDate);
    }

    public boolean hasEmployeePunchedInToday(Long employeeId) {
        return dao.hasPunchedInToday(employeeId);
    }

    public List<PunchInDto> getLatePunchIns(LocalDate date) {
        return dao.getLatePunchIns(date);
    }

    public List<PunchInDto> getEarlyPunchIns(LocalDate date) {
        return dao.getEarlyPunchIns(date);
    }

    private void validatePunchIn(PunchInDto punchIn) {
        if (punchIn.getEmployeeId() == null) {
            throw new IllegalArgumentException("Employee ID is required");
        }
        if (punchIn.getLoginLocation() == null || punchIn.getLoginLocation().trim().isEmpty()) {
            throw new IllegalArgumentException("Login location is required");
        }
    }

    private void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Both start and end dates are required");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
    }
} 