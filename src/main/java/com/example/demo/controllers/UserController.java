package com.example.demo.controllers;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Api(value="api", description="Operations pertaining to user")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/users/me")
    @ResponseStatus(HttpStatus.OK)
    public User getMyInfo() {
        return userService.getCurrentUser();
    }

    @GetMapping("/me/authorities")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getMyAuthorities() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

//    @PostMapping(value = Constants.URI_USERS)
//    @ResponseStatus(HttpStatus.CREATED)
//    public CreatedResourceDto registerUser(@RequestBody RegistrationForm form) {
//        return userService.createNewUser(form);
//    }

}
