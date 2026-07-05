package com.sicjac.backend;

import com.sicjac.backend.config.RenderDatabaseUrlConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        RenderDatabaseUrlConfig.apply();
        SpringApplication.run(BackendApplication.class, args);
    }
}
