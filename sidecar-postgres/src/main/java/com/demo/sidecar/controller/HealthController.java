package com.demo.sidecar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.sidecar.healthcheck.SidecarHealthIndicator;

/**
 * @author Sujith Simon
 * Created on : 14/08/20
 */
@RestController
@RequestMapping("/health")
public class HealthController {

    @Autowired
    private SidecarHealthIndicator healthIndicator;

    @GetMapping("/delegating-status")
    public Health sidecarHealthStatus() {
        return this.healthIndicator.health();
    }
}