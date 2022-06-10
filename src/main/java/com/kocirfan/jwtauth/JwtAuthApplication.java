package com.kocirfan.jwtauth;

import com.kocirfan.jwtauth.models.Role;
import com.kocirfan.jwtauth.models.User;
import com.kocirfan.jwtauth.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;

@SpringBootApplication
public class JwtAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtAuthApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserService userService){
        return args -> {
            userService.save(Role.builder().name("ROLE_USER").build());
            userService.save(Role.builder().name("ROLE_ADMIN").build());

            userService.save(User.builder().name("deneme").username("deneme1").password("1234").roles(new HashSet<>()).build());

            userService.addRoleTo("deneme1", "ROLE_USER");
        };
    }

}
