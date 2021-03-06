package com.mateuszziomek.issuestracker.issues.command;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class IssueCommandApplication {
    public static void main(String[] args) {
        SpringApplication.run(IssueCommandApplication.class, args);
    }
}
