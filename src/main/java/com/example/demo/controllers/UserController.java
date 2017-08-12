package com.example.demo.controllers;

import com.example.demo.entity.User;
import com.example.demo.reference.Constants;
import com.example.demo.service.UserService;
import com.example.demo.transfer.CreatedResourceDto;
import com.example.demo.transfer.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Igor Rybak
 */
@RestController
@RequestMapping(Constants.URI_API)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(Constants.URI_USERS + "/me")
    @PreAuthorize("hasRole('ROLE_USER')")
    public User getMyInfo() {
        return userService.getCurrentUser();
    }

    @GetMapping(Constants.URI_USERS + "/me/authorities")
    public List<String> getMyAuthorities() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    @PostMapping(value = Constants.URI_USERS)
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedResourceDto registerUser(@RequestBody RegistrationForm form) {
        return userService.createNewUser(form);
    }

}
