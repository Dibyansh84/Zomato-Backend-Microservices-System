package com.auth.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BCryptConfig
{

    /*
     * Create BCryptPasswordEncoder Bean
     * This bean is used for password encryption
     * and password matching in Spring Security
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        // Return BCryptPasswordEncoder object
        return new BCryptPasswordEncoder();
    }
}
