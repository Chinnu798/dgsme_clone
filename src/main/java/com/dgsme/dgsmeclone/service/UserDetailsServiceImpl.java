package com.dgsme.dgsmeclone.service;

import com.dgsme.dgsmeclone.dto.EmployeeLoginCredentialsDto;
import com.dgsme.dgsmeclone.repository.EmployeeLoginCredentialsRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final EmployeeLoginCredentialsRepository employeeRepo;

    public UserDetailsServiceImpl(EmployeeLoginCredentialsRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Fetch employee by email
        EmployeeLoginCredentialsDto employee = employeeRepo.findByEmployeeEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Employee not found with email: " + email));

        // Build UserDetails object
        return User.builder()
                .username(employee.getEmployeeEmail())  // email as username internally
                .password(employee.getEmployeePassword()) // password (should already be encoded in DB)
                .authorities(Collections.singleton(new SimpleGrantedAuthority(employee.getEmployeerole()))) // role
                .build();
    }
}
