package com.ephemeralin.z360;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * The type Application.
 */
@SpringBootApplication
@Configuration
@Slf4j
public class Application {

    private final Environment env;

    /**
     * Instantiates a new spring boot app.
     *
     * @param env the env
     */
    @Autowired
    public Application(Environment env) {
        this.env = env;
    }

    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
        log.info("testing logging with lombok");
    }
}
