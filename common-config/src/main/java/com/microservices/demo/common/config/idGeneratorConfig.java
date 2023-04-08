package com.microservices.demo.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.JdkIdGenerator;
import org.springframework.util.IdGenerator;

@Configuration
public class idGeneratorConfig {
    @Bean
    public IdGenerator idGenerator() {
        return new JdkIdGenerator();
    }
}
