package com.thryve.pgfinder.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;
import com.thryve.pgfinder.auth.model.Role;

public class AuthDtos {
    public static class RegisterRequest {
        @NotBlank @Size(min=3, max=64) public String username;
        @Email @NotBlank public String email;
        @NotBlank @Size(min=6, max=100) public String password;
        public Set<Role> roles;
        @NotBlank public String fullName;
        @NotBlank @Size(min=10, max=15) public String phone;
    }
    public static class LoginRequest {
        @NotBlank public String usernameOrEmail;
        @NotBlank public String password;
    }
    public static class TokenResponse {
        public String token;
        public String tokenType = "Bearer";
        public TokenResponse(String token) { this.token = token; }
    }
}
