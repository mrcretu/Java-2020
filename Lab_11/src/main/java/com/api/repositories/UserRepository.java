package com.api.repositories;


        import com.api.entities.User;
        import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByName(String name);
}