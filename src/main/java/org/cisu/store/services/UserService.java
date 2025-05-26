package org.cisu.store.services;

import jakarta.persistence.EntityManager;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.cisu.store.entities.User;
import org.cisu.store.repositories.AddressRepository;
import org.cisu.store.repositories.ProfileRepository;
import org.cisu.store.repositories.UserRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    /*
     the entityManager is responsible for managing the entities
     using the persistence context
     */
    private final EntityManager entityManager;
    private final AddressRepository addressRepository;

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


    @Transactional
    public void showRelatedEntities() {
        var profile = profileRepository.findById(1L).orElseThrow();
        System.out.println(profile.getUser().getEmail());
    }

    public void fetchAddress() {
        var address = addressRepository.findById(1L).orElseThrow();
    }

}
