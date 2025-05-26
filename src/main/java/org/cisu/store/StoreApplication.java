package org.cisu.store;

import org.cisu.store.entities.User;
import org.cisu.store.repositories.UserRepository;
import org.cisu.store.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class StoreApplication {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(StoreApplication.class, args);

//        var userCreate =  User.builder()
//                .name("John")
//                .email("john@gmail.com")
//                .password("password")
//                .build();
//
//        var repository = context.getBean(UserRepository.class);
//
//
//        repository.save(userCreate);

        // numbers in Java are Integers by default so we put a L at the end to
        // convert the Integer into Long type
//        repository.findById(1L).orElse(null);
//        var user = repository.findById(1L).orElseThrow();
//        repository.delete(user);

        // findAll returns an Iterable
//       repository.findAll().forEach(u -> System.out.println(u.getEmail()));
//       repository.deleteById(userCreate.getId());



        // Persistent
        var service = context.getBean(UserService.class);
//        service.showEntityStates();
//        service.showRelatedEntities();
        service.fetchAddress();




//        // add address
//        var address = Address.builder()
//                .street("Street")
//                .city("City")
//                .state("State")
//                .zip("zip")
//                .build();
//
//
////        user.getAddresses().add(address);
////        address.setUser(user);
//        user.addAddress(address);


//        // add tag
//        var tag = new Tag("tag1");
//        user.getTags().add(tag);
//        tag.getUsers().add(user);
//        user.addTag("tag1");

//        // add profile

//        var profile = Profile.builder()
//                        .bio("bio")
//                        .build();
//
//        user.setProfile(profile);
//        profile.setUser(user);
//
//        System.out.println(user);


    }

}
