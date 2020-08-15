package com.demo.bookstore.config;


import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Sujith Simon
 * Created on : 14/08/20
 */
@Configuration
@EnableConfigurationProperties({DataSourceProperties.class})
public class DatabaseConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfiguration.class);

    @Autowired
    private DataSourceProperties dsProperties;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${configuration.db-service-name}")
    private String dbServiceName;

    @Value("${configuration.db-connection-retry-gap-seconds}")
    private int sleepSeconds;

    @Value("${configuration.db-connection-max-retries}")
    private int maxRetries;


    @Bean
    @ConditionalOnProperty(name = "configuration.sd-enabled", havingValue = "false", matchIfMissing = true)
    public DataSource dataSourceFromProperties() throws InterruptedException, TimeoutException {
        return this.createDataSource(dsProperties.getUrl());
    }

    @Bean
    @ConditionalOnProperty(name = "configuration.sd-enabled", havingValue = "true", matchIfMissing = false)
    public DataSource dataSourceFromServiceDiscovery() throws InterruptedException, TimeoutException {
        ServiceInstance instance = blockThreadAndGetDatabaseService();
        final String remoteHost = instance.getHost();
        final int remotePort = instance.getPort();
        String jdbcUrl = dsProperties.getUrl().replace("{0}", remoteHost).replace("{1}", String.valueOf(remotePort));
        return this.createDataSource(jdbcUrl);
    }

    private DataSource createDataSource(String jdbcUrl) {
        return DataSourceBuilder
                .create()
                .url(jdbcUrl)
                .username(this.dsProperties.getUsername())
                .password(this.dsProperties.getPassword())
                .driverClassName(this.dsProperties.getDriverClassName())
                .build();
    }

    private ServiceInstance blockThreadAndGetDatabaseService() throws InterruptedException, TimeoutException {
        List<ServiceInstance> instances = this.discoveryClient.getInstances(this.dbServiceName);
        int retries = 0;
        while (instances.size() == 0 && retries < maxRetries) {
            logger.info("Finding database service attempt {}.", ++retries);
            Thread.sleep(TimeUnit.SECONDS.toMillis(sleepSeconds));
            instances = this.discoveryClient.getInstances(this.dbServiceName);
        }

        if (instances.size() == 0) {
            throw new TimeoutException("Could not find any database service. Stopping application.");
        }

        logger.info("Found the database service.");

        return instances.iterator().next();
    }

}
