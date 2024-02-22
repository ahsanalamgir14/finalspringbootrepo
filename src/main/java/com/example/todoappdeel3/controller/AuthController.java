package com.example.todoappdeel3.controller;

import com.example.todoappdeel3.config.JwtUtil;
import com.example.todoappdeel3.dao.UserRepository;
import com.example.todoappdeel3.dto.AuthenticationDTO;
import com.example.todoappdeel3.dto.LoginResponse;
import com.example.todoappdeel3.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;
    private UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO loginReq)  {

        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.email, loginReq.password));
            String email = authentication.getName();
            Optional<User> user = this.userRepository.findByEmail(email);
            String token = jwtUtil.createToken(user.get());
            LoginResponse loginRes = new LoginResponse(email,token);

            return ResponseEntity.ok(loginRes);

        }catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("errorResponse");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("errorResponse");
        }
    }
}
