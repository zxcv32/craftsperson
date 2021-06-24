package com.seacreeper.craftsperson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class CraftspersonApplication {

  public static void main(String[] args) {
    SpringApplication.run(CraftspersonApplication.class, args);
  }
}
