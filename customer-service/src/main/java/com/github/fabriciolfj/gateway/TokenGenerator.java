package com.github.fabriciolfj.gateway;

import io.quarkus.scheduler.Scheduled;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;

@Slf4j
@ApplicationScoped
public class TokenGenerator {

    private String token;

    //@Scheduled(every = "30s")
    @Scheduled(cron = "* * * * * ?")
    void generateToken() {
        token = UUID.randomUUID().toString();
        log.info("New token generated: {}", token);
    }

    public String getToken() {
        return token;
    }
}
