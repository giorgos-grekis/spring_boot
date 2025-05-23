package org.cisu.store;

import org.cisu.store.entities.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StoreApplication {

    public static void main(String[] args) {

//        ApplicationContext context = SpringApplication.run(StoreApplication.class, args);

        var user =  User.builder()
                .name("John")
                .email("john@gmail.com")
                .password("password")
                .build();

    }

}
