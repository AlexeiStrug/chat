package com.example.demo.service;


import com.example.demo.entity.User;
import com.example.demo.transfer.CreatedResourceDto;
import com.example.demo.transfer.RegistrationForm;

/**
 * @author Igor Rybak
 */
public interface UserService {
    User getCurrentUser();

    CreatedResourceDto createNewUser(RegistrationForm user);
}
