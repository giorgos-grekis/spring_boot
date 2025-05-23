package org.cisu.store;

import org.cisu.store.entities.Address;
import org.cisu.store.entities.Profile;
import org.cisu.store.entities.Tag;
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

        var profile = Profile.builder()
                        .bio("bio")
                        .build();

        user.setProfile(profile);
        profile.setUser(user);

        System.out.println(user);


    }

}
