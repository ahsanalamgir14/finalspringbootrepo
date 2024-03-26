package com.example.todoappdeel3.controller;

import com.example.todoappdeel3.Enums.OrderStatus;
import com.example.todoappdeel3.Enums.UserRole;
import com.example.todoappdeel3.config.JWTUtil;
import com.example.todoappdeel3.models.Order;
import com.example.todoappdeel3.repository.OrderRepository;
import com.example.todoappdeel3.repository.UserRepository;
import com.example.todoappdeel3.dto.AuthenticationDTO;
import com.example.todoappdeel3.dto.LoginResponse;
import com.example.todoappdeel3.models.CustomUser;
import com.example.todoappdeel3.services.CredentialValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins ={"http://localhost:4200", "http://s1149822.student.inf-hsleiden.nl:19822"})
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userDAO;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authManager;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;
    private CredentialValidator validator;

    public AuthController(UserRepository userDAO, JWTUtil jwtUtil, AuthenticationManager authManager, OrderRepository orderRepository,
                          PasswordEncoder passwordEncoder, CredentialValidator validator) {
        this.userDAO = userDAO;
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
        this.orderRepository = orderRepository;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody AuthenticationDTO authenticationDTO) {
        if (!validator.isValidEmail(authenticationDTO.email)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No valid email provided"
            );
        }

        if (!validator.isValidPassword(authenticationDTO.password)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No valid password provided"
            );
        }

        CustomUser customUser = userDAO.findByEmail(authenticationDTO.email);

        if (customUser != null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Can not register with this email"
            );
        }

        String encodedPassword = passwordEncoder.encode(authenticationDTO.password);
        CustomUser registerdCustomUser = new CustomUser(authenticationDTO.email, encodedPassword, UserRole.USER);
        userDAO.save(registerdCustomUser);
        String token = jwtUtil.generateToken(registerdCustomUser.getEmail());
        Order neworder = new Order();
        neworder.setPrice(0.0);
        neworder.setOrderStatus(OrderStatus.Pending);
        neworder.setUser(registerdCustomUser);
        orderRepository.save(neworder);
        CustomUser registeredUserDatabase = userDAO.findByEmail(authenticationDTO.email);
        LoginResponse loginResponse = new LoginResponse( registeredUserDatabase.getId(), registerdCustomUser.getEmail(), token);
        return ResponseEntity.ok(loginResponse);
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody AuthenticationDTO body) {
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.email, body.password);

            authManager.authenticate(authInputToken);

            String token = jwtUtil.generateToken(body.email);

            CustomUser customUser = userDAO.findByEmail(body.email);
            LoginResponse loginResponse = new LoginResponse(customUser.getId(), customUser.getEmail(), token);


            return ResponseEntity.ok(loginResponse);

        } catch (AuthenticationException authExc) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "No valid credentials"
            );
        }
    }

}
