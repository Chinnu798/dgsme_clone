//package com.dgsme.dgsmeclone.controller;
//
//import com.dgsme.dgsmeclone.service.UserDetailsServiceImpl;
//import com.dgsme.dgsmeclone.util.JwtUtil;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/auth")
////@RequiredArgsConstructor
//public class AuthController {
//
//    private final AuthenticationManager authenticationManager;
//    private final JwtUtil jwtUtil;
//    private final com.dgsme.dgsmeclone.service.UserDetailsServiceImpl userDetailsService;
//    
//    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
//        this.authenticationManager = authenticationManager;
//        this.jwtUtil = jwtUtil;
//        this.userDetailsService = userDetailsService;
//    }
//
//
//
//	@PostMapping("/login")
//    public String login(@RequestParam String username, @RequestParam String password) {
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(username, password)
//            );
//        } catch (AuthenticationException e) {
//            throw new RuntimeException("Invalid username/password");
//        }
//
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//        return jwtUtil.generateToken(userDetails);
//    }
//}






































package com.dgsme.dgsmeclone.controller;

import com.dgsme.dgsmeclone.dto.LoginRequest;
import com.dgsme.dgsmeclone.service.UserDetailsServiceImpl;
import com.dgsme.dgsmeclone.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(), 
                            loginRequest.getPassword()
                    )
            );

            // Load user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

            // Generate JWT
            return jwtUtil.generateToken(userDetails);

        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username/password", e);
        }
    }
}
















//
//package com.dgsme.dgsmeclone.controller;
//
//import com.dgsme.dgsmeclone.service.UserDetailsServiceImpl;
//import com.dgsme.dgsmeclone.util.JwtUtil;
//
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    private final AuthenticationManager authenticationManager;
//    private final JwtUtil jwtUtil;
//    private final UserDetailsServiceImpl userDetailsService;
//    private final BCryptPasswordEncoder passwordEncoder;
//
//    // Constructor injection
//    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder passwordEncoder) {
//        this.authenticationManager = authenticationManager;
//        this.jwtUtil = jwtUtil;
//        this.userDetailsService = userDetailsService;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @PostMapping("/login")
//    public String login(@RequestParam String username, @RequestParam String password) {
//        // Load user details
//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//        // Check if the password matches the encoded password
//        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
//            throw new RuntimeException("Invalid username/password");
//        }
//
//        // Authenticate the user if the password is correct
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(username, password)
//            );
//        } catch (AuthenticationException e) {
//            throw new RuntimeException("Authentication failed");
//        }
//
//        // Generate JWT token for the authenticated user
//        return jwtUtil.generateToken(userDetails);
//    }
//}
