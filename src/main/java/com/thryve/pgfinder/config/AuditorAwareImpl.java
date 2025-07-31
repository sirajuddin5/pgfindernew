//package com.thryve.pgfinder.config;
//
//import java.util.Optional;
//
//import org.springframework.data.domain.AuditorAware;
//import org.springframework.stereotype.Service;
//import java.util.Optional;
//import org.springframework.data.domain.AuditorAware;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//
//@Service
//public class AuditorAwareImpl implements AuditorAware<String> {
//
//	@Override
//	public Optional<String> getCurrentAuditor() {
//		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//	        if (!(authentication instanceof AnonymousAuthenticationToken)) {
//	            String currentUserName = authentication.getName();
//	           // PrintUtil.println("currentUserName", currentUserName);
//	            return Optional.of(currentUserName);
//	        } else {
//	            return null;
//	        }
//	}
//
//}
