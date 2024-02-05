package com.zeeyeh.versionmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan(basePackages = {
//        "com.zeeyeh.versionmanager.interceptors"
//})
public class VersionManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(VersionManagerApplication.class, args);
    }
}
