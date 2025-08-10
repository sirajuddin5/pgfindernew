package com.thryve.pgfinder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thryve.pgfinder.mapper.PGMapper;
import com.thryve.pgfinder.mapper.PGMapperImpl;

@Configuration
public class MapperConfig {

    @Bean
    public PGMapper pgMapper() {
        return new PGMapperImpl(); // Make sure this class is generated
    }
}

