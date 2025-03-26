package org.example;

import org.example.service.MainService;
import org.example.service.kafka.KafkaAdminClientUtil;
import org.example.service.kafka.KafkaConsumerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties({LiquibaseProperties.class})
public class Main {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(Main.class, args);
        // Lấy MainService từ Spring Context
//        MainService mainService = context.getBean(MainService.class);
        //        mainService.showMenu();
        KafkaAdminClientUtil kafkaAdminClientUtil = context.getBean(KafkaAdminClientUtil.class);
        System.out.println("hehehehe: " + kafkaAdminClientUtil.getAllTopics());
        System.out.println("hohojo: " + kafkaAdminClientUtil.getAllBrokers());
        System.out.println("kekeke: " + kafkaAdminClientUtil.getClusterId());
    }
}