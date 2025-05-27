package org.cisu.store.services;

import jakarta.persistence.EntityManager;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.cisu.store.entities.*;
import org.cisu.store.repositories.AddressRepository;
import org.cisu.store.repositories.ProfileRepository;
import org.cisu.store.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
    private final ProductRepository productRepository;

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

    public void persistRelated() {
        var user = User.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .password("password")
                .build();

        var address =  Address.builder()
                .street("street")
                .city("city")
                .state("state")
                .zip("zip")
                .build();

        user.addAddress(address);

        userRepository.save(user);
    }

    // When we try to delete an entity Hibernate fetch that entity
    // with relation to make sure data is correct
    @Transactional
    public void deleteRelated() {
        var user = userRepository.findById(3L).orElseThrow();
        var address = user.getAddresses().getFirst();
        user.removeAddress(address);
        userRepository.save(user);
    }

    public void manageProducts() {

        var category = new Category("Category 1");

        var product = Product.builder()
                .name("Product 1")
                .description("Product description")
                .price(BigDecimal.valueOf(10.99))
                .category(category)
                .build();

        productRepository.save(product);

    }

}
