package com.demo.sidecar.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.demo.sidecar.healthcheck.SidecarHealthIndicator;
import com.demo.sidecar.healthcheck.impl.PostgresHealthCheck;

/**
 * @author Sujith Simon
 * Created on : 13/08/20
 */
@Configuration
public class SidecarConfiguration {

    private static final String POSTGRES_TYPE = "postgres";

    @ConditionalOnProperty(name = "configuration.sidecar.type", havingValue = POSTGRES_TYPE, matchIfMissing = false)
    @Bean
    public SidecarHealthIndicator postgresHealthCheck() {
        return new PostgresHealthCheck();
    }
}
