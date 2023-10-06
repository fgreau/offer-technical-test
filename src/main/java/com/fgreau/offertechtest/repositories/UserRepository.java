package com.fgreau.offertechtest.repositories;


import com.fgreau.offertechtest.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

}
