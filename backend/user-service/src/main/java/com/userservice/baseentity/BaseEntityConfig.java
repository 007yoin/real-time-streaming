package com.userservice.baseentity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class BaseEntityConfig {

    @Bean
    public AuditorAware<Long> auditorProvider() {
        // TODO 당장은 임시 값
        return () -> Optional.of(1L);
    }

}
