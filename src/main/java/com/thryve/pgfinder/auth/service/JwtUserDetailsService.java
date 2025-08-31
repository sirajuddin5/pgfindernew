package com.thryve.pgfinder.auth.service;

import java.util.stream.Collectors;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.thryve.pgfinder.auth.repository.AuthUserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final AuthUserRepository repo;
    public JwtUserDetailsService(AuthUserRepository repo) { this.repo = repo; }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        var user = repo.findByUsername(usernameOrEmail)
            .or(() -> repo.findByEmail(usernameOrEmail))
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + usernameOrEmail));
        var authorities = user.getRoles().stream()
            .map(r -> "ROLE_" + r.name())
            .map(org.springframework.security.core.authority.SimpleGrantedAuthority::new)
            .collect(Collectors.toSet());
        return new User(user.getUsername(), user.getPasswordHash(), authorities);
    }
}
