package com.dgsme.dgsmeclone.dao;

import com.dgsme.dgsmeclone.dto.EmployeeLoginCredentialsDto;
import com.dgsme.dgsmeclone.repository.EmployeeLoginCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmployeeLoginCredentialsDao {

    @Autowired
    private EmployeeLoginCredentialsRepository repository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public EmployeeLoginCredentialsDto saveCredentials(EmployeeLoginCredentialsDto credentialsDto) {
        String originalPassword = credentialsDto.getEmployeePassword();
        String hashedPassword = passwordEncoder.encode(originalPassword);
        credentialsDto.setEmployeePassword(hashedPassword);
        return repository.save(credentialsDto);
    }

    public boolean validateUser(String email, String password) {
        Optional<EmployeeLoginCredentialsDto> userOpt = repository.findByEmployeeEmail(email.trim());
        return userOpt.map(user ->
            passwordEncoder.matches(password, user.getEmployeePassword())
        ).orElse(false);
    }
}
