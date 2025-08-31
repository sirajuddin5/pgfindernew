package com.thryve.pgfinder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thryve.pgfinder.mapper.PGMapper;
import org.mapstruct.factory.Mappers;

@Configuration
public class MapperConfig {

    @Bean
    public PGMapper pgMapper() {
        return Mappers.getMapper(PGMapper.class); // Make sure this class is generated
    }
}

