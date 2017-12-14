package com.epam.vkneo4j;

import com.epam.vkneo4j.repository.GroupRepository;
import com.epam.vkneo4j.repository.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Vkneo4jApplication {

  public static void main(String[] args) {
    SpringApplication.run(Vkneo4jApplication.class, args);
  }

  @Bean
  CommandLineRunner demo(PersonRepository personRepository, GroupRepository groupRepository) {
    return args -> {
      personRepository.deleteAll();
      groupRepository.deleteAll();
    };
  }
}
