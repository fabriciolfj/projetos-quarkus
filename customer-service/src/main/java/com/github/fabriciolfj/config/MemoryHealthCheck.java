package com.github.fabriciolfj.config;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Liveness;

import javax.enterprise.context.ApplicationScoped;

@Liveness
@ApplicationScoped
public class MemoryHealthCheck implements HealthCheck {
     long threshold = 1924000000;

    @Override
    public HealthCheckResponse call() {
        final HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("memory");
        /*final long freeMemory = Runtime.getRuntime().freeMemory();

        if (freeMemory >= threshold) {
            responseBuilder.up();
        } else {
            responseBuilder.down()
            .withData("error", "Not enough free memory! Please restart application");
        }

        return responseBuilder.build();*/
        return responseBuilder.up().build();
    }
}
