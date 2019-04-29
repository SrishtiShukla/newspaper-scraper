package com.srishti.webscrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.srishti.webscrapper.repo")
@EntityScan("com.srishti.webscrapper.model")
@SpringBootApplication
public class WebscrapperApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebscrapperApplication.class, args);
    }

}
