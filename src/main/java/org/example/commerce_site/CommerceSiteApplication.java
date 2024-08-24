package org.example.commerce_site;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication (exclude = SecurityAutoConfiguration.class)
public class CommerceSiteApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommerceSiteApplication.class, args);
    }
}
