package com.example.registration.repository;

import com.example.registration.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Alex on 20.08.2017.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    Optional<User> findByLogin(String login);

    @Override
    void delete(User user);
}
