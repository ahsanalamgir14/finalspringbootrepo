package com.example.todoappdeel3.dto;

public class LoginResponse {
    public String email;
    public String token;

    public  Long id;

    public LoginResponse(Long id, String email, String token) {
        this.email = email;
        this.token = token;
        this.id = id;
    }
}
