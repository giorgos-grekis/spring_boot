package org.cisu.store.repositories;

import org.cisu.store.entities.User;
import org.springframework.data.repository.CrudRepository;


/*
CrudRepository has two generate type parameters
first "User" => is the entity we need to create the repository

second "Long" => Is the type of primary key
 */
public interface UserRepository extends CrudRepository<User, Long> {
}
