package com.thryve.pgfinder.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thryve.pgfinder.auth.dto.AuthDtos.*;
import com.thryve.pgfinder.auth.model.AuthUser;
import com.thryve.pgfinder.auth.model.Role;
import com.thryve.pgfinder.auth.repository.AuthUserRepository;
import com.thryve.pgfinder.common.dto.ApiResponse;
import com.thryve.pgfinder.repository.OwnerRepository;
import com.thryve.pgfinder.model.Owner;
import com.thryve.pgfinder.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthUserRepository repo;
    private final OwnerRepository ownerRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthUserRepository repo, OwnerRepository ownerRepository,PasswordEncoder encoder, JwtUtil jwtUtil, AuthenticationProvider provider) {
        this.repo = repo; this.ownerRepository = ownerRepository; this.encoder = encoder; this.jwtUtil = jwtUtil;
        this.authenticationManager = new ProviderManager(provider);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated @RequestBody RegisterRequest request) {
        if (repo.existsByUsername(request.username)) return ResponseEntity.badRequest().body(ApiResponse.error("Username already exists"));
        if (repo.existsByEmail(request.email)) return ResponseEntity.badRequest().body(ApiResponse.error("Email already exists"));
        var user = new AuthUser();
        user.setUsername(request.username);
        user.setEmail(request.email);
        user.setFullName(request.fullName);
        user.setPhone(request.phone);
        user.setPasswordHash(encoder.encode(request.password));
        user.setPwd(request.password);
        Set<Role> roles = (request.roles == null || request.roles.isEmpty()) ? Set.of(Role.TENANT) : request.roles;
        user.setRoles(roles);
        user.setOwnerId(null); user.setTenantId(null); user.setAdminId(null); // do not require these in request

        var saved = repo.save(user);

        // Create domain profiles as needed
        if (roles.contains(Role.OWNER)) {
        	
        	Owner owner = new Owner();
        	owner.setFullName(request.fullName);
        	owner.setEmail(request.email);
        	owner.setUsername(request.username);
        	owner.setPhone(request.phone);
        	owner.setAuthUserId(String.valueOf(saved.getId()));

        	ownerRepository.save(owner);
        	
        }
        // TENANT / ADMIN profiles can be added similarly when corresponding entities exist

        String token = jwtUtil.generateToken(saved.getUsername(), roles.stream().map(Enum::name).collect(Collectors.toSet()));
        var resp = new TokenResponse(token);
        Map<String, Object>registerRes= new HashMap<>();
        registerRes.put("tokenResponse", resp);
        registerRes.put("user", saved);
        System.out.println("resgister Response : "+registerRes);
        var apiResponse = ApiResponse.ok("Registered successfully", registerRes);
        
        return ResponseEntity.ok(apiResponse);
    }
@PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.usernameOrEmail, request.password));
        SecurityContextHolder.getContext().setAuthentication(auth);
        var username = auth.getName();
        var user = repo.findByUsername(username).orElseThrow();
        var roles = user.getRoles().stream().map(Enum::name).collect(Collectors.toSet());
        String token = jwtUtil.generateToken(username, roles);
        
        Map<String, Object>registerRes= new HashMap<>();
        registerRes.put("tokenResponse", token);
        registerRes.put("roles", roles);
        registerRes.put("user", user);
        System.out.println("resgister Response : "+registerRes);
        var apiResponse = ApiResponse.ok("Registered successfully", registerRes);
        System.out.println("API Response success: " + apiResponse.isSuccess());
        System.out.println("API Response message: " + apiResponse.getMessage());
        System.out.println("API Response data: " + apiResponse.getData());
        
        
        return ResponseEntity.ok(apiResponse);
    }
}
