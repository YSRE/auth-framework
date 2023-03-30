package com.shy.security.config;

import com.shy.security.TokenManager;
import com.shy.security.impl.JwtTokenManagerImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author shy
 * @date 2023/3/28
 */
@AutoConfiguration
@ComponentScan(basePackages = {"com.shy.auth","com.shy.auth"})
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityAutoConfiguration {

    @Bean
    public TokenManager tokenManager(){
        return new JwtTokenManagerImpl();
    }
}
