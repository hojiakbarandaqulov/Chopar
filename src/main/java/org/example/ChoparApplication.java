package org.example;

import org.example.util.MD5Util;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChoparApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChoparApplication.class, args);
        System.out.println(MD5Util.getMD5("1234"));
    }
}
