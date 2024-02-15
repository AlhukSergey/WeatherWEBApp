package com.alshuk.weather;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableScheduling
@EnableFeignClients
@PropertySource("classpath:custom.properties")
public class WeatherApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(WeatherApplication.class);
        Environment env = application.run(args).getEnvironment();
        log.info("""
                        ----------------------------------------------------------
                        \tApplication '{}' is running! Access URLs:
                        \tLocal: \t\t{}://localhost:{}{}
                        ----------------------------------------------------------""",
                env.getProperty("spring.application.name"), "http", env.getProperty("server.port"), "/home");
    }

}
