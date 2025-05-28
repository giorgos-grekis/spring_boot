package org.cisu.store.services;

import jakarta.persistence.EntityManager;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.cisu.store.entities.Address;
import org.cisu.store.entities.Product;
import org.cisu.store.entities.User;
import org.cisu.store.repositories.AddressRepository;
import org.cisu.store.repositories.ProductRepository;
import org.cisu.store.repositories.ProfileRepository;
import org.cisu.store.repositories.UserRepository;
import org.cisu.store.repositories.specifications.ProductSpec;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
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

    @Transactional
    public void updateProductPrices() {
        productRepository.updatePriceByCategory(BigDecimal.valueOf(10), (byte) 1);
    }


    @Transactional
    public void fetchProducts() {
//       var products = productRepository.findByCategory(new Category((byte)1));
//       var products = productRepository.findProductsProcedure(BigDecimal.valueOf(1)
//               ,BigDecimal.valueOf(15));
//        products.forEach(System.out::println);
        var product = new Product();
        product.setName("product");

        var matcher =  ExampleMatcher.matching()
                .withIncludeNullValues()
                .withIgnorePaths("id", "description")
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        var example = Example.of(product, matcher);
        var products = productRepository.findAll(example);
        products.forEach(System.out::println);
    }

    public void fetchProductsByCriteria() {
        var products = productRepository.findProductsByCriteria("prod",
                BigDecimal.valueOf(1), null);

        products.forEach(System.out::println);
    }

    public void fetchProductsBySpecifications(String name,
                                              BigDecimal minPrice,
                                              BigDecimal maxPrice) {
        Specification<Product> spec = Specification.where(null);

        if (name != null) {
            spec = spec.and(ProductSpec.hasName(name));
        }
        if (minPrice != null) {
            spec = spec.and(ProductSpec.hasPriceGreaterThanOrEqualTo(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductSpec.hasPriceLessThanOrEqualTo(maxPrice));
        }

        productRepository.findAll(spec).forEach(System.out::println);
    }

    public void fetchSortedProducts() {
        var sort =  Sort.by("name")
                .and(Sort.by("price").descending());

        productRepository.findAll(sort).forEach(System.out::println);
    }

    public void fetchPaginatedProducts(int pageNumber, int size) {
        PageRequest pageRequest = PageRequest.of(pageNumber, size);
        Page<Product> page =  productRepository.findAll(pageRequest);

        var products =  page.getContent();
        products.forEach(System.out::println);

        var totalPages = page.getTotalPages();
        var totalElements = page.getTotalElements();
        System.out.println("Total Pages: " + totalPages);
        System.out.println("Total Elements: " + totalElements);


    }

    @Transactional
    public void fetchUser() {
        var user = userRepository.findByEmail("test@test.com").orElseThrow();
        System.out.println(user);
    }

    @Transactional
    public void fetchUsers() {
        var users = userRepository.findAllWihAddresses();
        users.forEach(u -> {
            System.out.println(u);
            u.getAddresses().forEach(System.out::println);
        });
    }

    @Transactional
    public void printLoyalProfiles() {
        var users = userRepository.findLoyalUsers(2);
                users.forEach(p ->
                        System.out.println(p.getId()+ ": "  + p.getEmail()));
    }

}
