package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.reference.errors.NoUserWithSuchUsernameCustomException;
import com.example.demo.repositories.UserRepository;
import com.example.demo.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final SecurityUtils securityUtils;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, SecurityUtils securityUtils) {
        this.userRepository = userRepository;
        this.securityUtils = securityUtils;
    }


    @Override
    public User getCurrentUser() {
        User user = userRepository.findByUsername(securityUtils.getCurrentUserLogin())
                .orElseThrow(NoUserWithSuchUsernameCustomException::new);
        return user;
    }

//    @Override
//    public CreatedResourceDto createNewUser(RegistrationForm form) {
//        if (userRepository.findByUsername(form.getUsername()).isPresent())
//            throw new UserWithSuchUsernameAlreadyExistsCustomException();
//
//        User user = new User();
//        user.setUsername(form.getUsername());
//        user.setPassword(form.getPassword());
//        user.setRole("ROLE_USER");
//        userRepository.save(user);
//        return new CreatedResourceDto(user.getId());
//    }

}
