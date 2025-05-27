package org.cisu.store.repositories;

import org.cisu.store.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


/*
CrudRepository has two generate type parameters
first "User" => is the entity we need to create the repository

second "Long" => Is the type of primary key
 */
public interface UserRepository extends CrudRepository<User, Long> {

    @EntityGraph(attributePaths = {"tags", "addresses"})
    Optional<User> findByEmail(String email);

    @EntityGraph(attributePaths = "addresses")

    @Query("select u from User u")
    List<User> findAllWihAddresses();
}
