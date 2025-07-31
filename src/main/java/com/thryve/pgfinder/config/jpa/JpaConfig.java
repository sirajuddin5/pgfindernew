//package com.thryve.pgfinder.config.jpa;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.domain.AuditorAware;
//import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//
//import com.thryve.pgfinder.config.AuditorAwareImpl;
//
//@Configuration
//@EnableJpaAuditing(auditorAwareRef = "auditorAware")
//public class JpaConfig {
//	   public JpaConfig() {
//	    }
//	
//	 @Bean
//	    public AuditorAware<String> auditorAware() {
//	        return new AuditorAwareImpl();
//	    }
//
//}
