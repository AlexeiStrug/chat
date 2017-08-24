package com.example.registration.service;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.Map;

/**
 * Created by Alex on 21.08.2017.
 */
public interface ISecurityUserService {

    Map<String, OAuth2AccessToken> validatePasswordResetToken(long id, String token);
}
