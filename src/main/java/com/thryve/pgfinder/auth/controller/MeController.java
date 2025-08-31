package com.thryve.pgfinder.auth.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import com.thryve.pgfinder.model.Owner;
import com.thryve.pgfinder.model.common.APIResponse;
import com.thryve.pgfinder.repository.OwnerRepository;

@RestController
@RequestMapping("/api/me")
public class MeController {
	
	@Autowired
	private OwnerRepository ownerRepository;
	
    @GetMapping("/owner") @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> ownerOnly(Authentication authentication) { 
    APIResponse response = new APIResponse();
	// APIResponse user = extractUserDetails(authentication);
	UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	String username = userDetails.getUsername();
	Optional<Owner> optionalOwner = ownerRepository.findByUsername(username);
    if (optionalOwner.isEmpty()) {
        return ResponseEntity.badRequest().body(new APIResponse("error", "Owner not found", null));
    }
    
    Owner owner = optionalOwner.get();
    response.setStatus("success");
    response.setMessage("Owner fetched successfully");
    response.setResult(owner);
    

	return ResponseEntity.ok(response); 
	}

    @GetMapping("/tenant") @PreAuthorize("hasRole('TENANT')")
    public String tenantOnly() { return "hello tenant"; }

    @GetMapping("/admin") @PreAuthorize("hasRole('ADMIN')")
    public String adminOnly() { return "hello admin"; }
    
   
    
}




//
//@RestController
//@RequestMapping("/api/me")
//public class MeController {
//
//    @GetMapping("/owner")
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<UserResponse> ownerOnly(Authentication authentication) {
//        UserResponse user = extractUserDetails(authentication);
//        return ResponseEntity.ok(user);
//    }
//
//    @GetMapping("/tenant")
//    @PreAuthorize("hasRole('TENANT')")
//    public ResponseEntity<UserResponse> tenantOnly(Authentication authentication) {
//        UserResponse user = extractUserDetails(authentication);
//        return ResponseEntity.ok(user);
//    }
//
//    @GetMapping("/admin")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<UserResponse> adminOnly(Authentication authentication) {
//        UserResponse user = extractUserDetails(authentication);
//        return ResponseEntity.ok(user);
//    }
//
//    private UserResponse extractUserDetails(Authentication authentication) {
//        // Default Spring Security's UserDetails
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//
//        return new UserResponse(
//                userDetails.getUsername(),
//                userDetails.getAuthorities()
//                        .stream()
//                        .map(GrantedAuthority::getAuthority)
//                        .toList()
//        );
//    }
//
//    // Custom DTO to return user info
//    public static class UserResponse {
//        private String username;
//        private List<String> roles;
//
//        public UserResponse(String username, List<String> roles) {
//            this.username = username;
//            this.roles = roles;
//        }
//
//        public String getUsername() {
//            return username;
//        }
//
//        public List<String> getRoles() {
//            return roles;
//        }
//    }
//}
