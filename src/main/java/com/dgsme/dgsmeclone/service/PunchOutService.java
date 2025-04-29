package com.dgsme.dgsmeclone.service;

import com.dgsme.dgsmeclone.dao.PunchOutDao;
import com.dgsme.dgsmeclone.dto.PunchOutDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class PunchOutService {
    
    private final PunchOutDao dao;

    @Autowired
    public PunchOutService(PunchOutDao dao) {
        this.dao = dao;
    }

    public PunchOutDto recordPunchOut(PunchOutDto punchOut) {
        validatePunchOut(punchOut);
        
        punchOut.setLogoutDate(LocalDate.now());
        punchOut.setLogoutTime(LocalTime.now());
        
        return dao.savePunchOut(punchOut);
    }

    public List<PunchOutDto> getEmployeePunchOuts(Long employeeId, LocalDate date) {
        return dao.getPunchOutsByEmployeeAndDate(employeeId, date);
    }

    public Optional<PunchOutDto> getLastPunchOut(Long employeeId) {
        return dao.getLastPunchOut(employeeId);
    }

    public List<PunchOutDto> getPunchOutsByDateRange(Long employeeId, LocalDate startDate, LocalDate endDate) {
        validateDateRange(startDate, endDate);
        return dao.getPunchOutsByDateRange(employeeId, startDate, endDate);
    }

    public boolean hasEmployeePunchedOutToday(Long employeeId) {
        return dao.hasPunchedOutToday(employeeId);
    }

    public List<PunchOutDto> getEarlyPunchOuts(LocalDate date) {
        return dao.getEarlyPunchOuts(date);
    }

    public List<PunchOutDto> getLatePunchOuts(LocalDate date) {
        return dao.getLatePunchOuts(date);
    }

    public List<PunchOutDto> getPunchOutsByTimeRange(Long employeeId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        validateTimeRange(startTime, endTime);
        return dao.getPunchOutsByTimeRange(employeeId, date, startTime, endTime);
    }

    private void validatePunchOut(PunchOutDto punchOut) {
        if (punchOut.getEmployeeId() == null) {
            throw new IllegalArgumentException("Employee ID is required");
        }
        if (punchOut.getLogoutLocation() == null || punchOut.getLogoutLocation().trim().isEmpty()) {
            throw new IllegalArgumentException("Logout location is required");
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

    private void validateTimeRange(LocalTime startTime, LocalTime endTime) {
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("Both start and end times are required");
        }
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time cannot be after end time");
        }
    }
} 