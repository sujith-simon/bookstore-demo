package com.demo.sidecar.healthcheck.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;

import com.demo.sidecar.healthcheck.SidecarHealthIndicator;

/**
 * @author Sujith Simon
 * Created on : 13/08/20
 */
public class PostgresHealthCheck implements SidecarHealthIndicator {

    private static final Logger logger = LoggerFactory.getLogger(PostgresHealthCheck.class);


    private static final String COMMAND_PATTERN = "pg_isready -U %s -h localhost -p %s";

    @Value("${sidecar.port}")
    private int postgresPort;

    @Value("${postgres.user}")
    private String postgresUser;

    @Override
    public Health health() {
        Health.Builder result = null;
        try {
            String output = runCommand();
            if (output.contains("accepting connections")) {
                result = Health.up();
            } else if (output.contains("rejecting connections") || output.contains("no response")) {
                result = Health.down().withDetail("reason", output);
            } else {
                result = Health.down().withDetail("reason", "unknown");
            }
        } catch (IOException e) {
            logger.error("Failed to execute command.", e);
            result = Health.down().withException(e);
        }
        return result.build();
    }

    private String runCommand() throws IOException {
        final String command = String.format(COMMAND_PATTERN, postgresUser, postgresPort);
        logger.debug("Executing command {}", command);
        Process process = Runtime.getRuntime().exec(command);

        String commandOutput = "";
        if (process.getInputStream() != null) {
            commandOutput += IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8);
        }
        if (process.getErrorStream() != null) {
            commandOutput += IOUtils.toString(process.getErrorStream(), StandardCharsets.UTF_8);
        }

        logger.debug("Got command output {}", commandOutput);

        return commandOutput;
    }
}
