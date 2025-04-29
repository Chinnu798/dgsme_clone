package com.dgsme.dgsmeclone.service;

import com.dgsme.dgsmeclone.dao.EmployeeLoginCredentialsDao;
import com.dgsme.dgsmeclone.dto.EmployeeLoginCredentialsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeLoginCredentialsService {

    @Autowired
    private EmployeeLoginCredentialsDao dao;

    public EmployeeLoginCredentialsDto save(EmployeeLoginCredentialsDto dto) {
        return dao.saveCredentials(dto);
    }

    public boolean validateCredentials(String email, String password) {
        return dao.validateUser(email, password);
    }
}
