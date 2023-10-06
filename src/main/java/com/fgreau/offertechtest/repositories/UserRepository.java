package com.fgreau.offertechtest.repositories;


import com.fgreau.offertechtest.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {

    /**
     * Get the user corresponding to its username, provided it hasn't been deleted.
     *
     * @param username username
     * @return user entity
     */
    Optional<User> findByUsernameAndDeletedFalse(String username);
}
