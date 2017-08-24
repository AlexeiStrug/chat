package com.example.registration.repository;

import com.example.registration.model.User;
import com.example.registration.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Alex on 20.08.2017.
 */
@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}
