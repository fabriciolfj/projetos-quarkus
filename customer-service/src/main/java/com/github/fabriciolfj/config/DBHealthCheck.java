package com.github.fabriciolfj.config;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.net.Socket;

@ApplicationScoped
public class DBHealthCheck implements HealthCheck {

    @ConfigProperty(name = "db.host")
    String host;

    @ConfigProperty(name = "db.port")
    Integer port;

    @Override
    public HealthCheckResponse call() {
        final HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("Database connection health check");

        try {
            serverListening(host, port);
            responseBuilder.up();
        } catch (IOException e) {
            responseBuilder.down()
                    .withData("error", e.getMessage());
        }

        return responseBuilder.build();
    }

    private void serverListening(final String host, final Integer port) throws IOException {
        final Socket s = new Socket(host, port);
        s.close();;
    }
}
