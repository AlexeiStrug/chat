package com.example.registration.service;

import com.example.registration.config.security.CustomUserDetails;
import com.example.registration.model.User;
import com.example.registration.repository.UserRepository;
import com.example.registration.validation.exception.UserIsDisableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));

        return new CustomUserDetails(user);
    }
}
