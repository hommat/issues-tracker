package org.example.issuestracker.issues.command;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:beans.xml")
public class IssueCommandApplication {
    public static void main(String[] args) {
        SpringApplication.run(IssueCommandApplication.class, args);
    }
}
