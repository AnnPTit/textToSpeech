package org.example;

import org.example.service.MainService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
@SpringBootApplication
@EnableConfigurationProperties({ LiquibaseProperties.class})
public class Main {
    private static MainService mainService = new MainService();

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MainService.class, args);
        mainService.showMenu();
    }
}