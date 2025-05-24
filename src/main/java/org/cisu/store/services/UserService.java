package org.cisu.store.services;

import jakarta.persistence.EntityManager;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.cisu.store.entities.User;
import org.cisu.store.repositories.UserRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    /*
     the entityManager is responsible for managing the entities
     using the persistence context
     */
    private final EntityManager entityManager;

    @Transactional
    public void showEntityStates() {
        var user = User.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .password("password")
                .build();

        if (entityManager.contains(user))
            System.out.println("Persistent");
        else
            System.out.println("Transient or Detached");


        userRepository.save(user);

        if (entityManager.contains(user))
            System.out.println("Persistent after save");
        else
            System.out.println("Transient or Detached after save");
    }

}
