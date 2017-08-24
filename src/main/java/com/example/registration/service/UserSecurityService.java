package com.example.registration.service;

import com.example.registration.model.PasswordResetToken;
import com.example.registration.model.User;
import com.example.registration.repository.PasswordResetTokenRepository;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Alex on 21.08.2017.
 */
@Service
public class UserSecurityService implements ISecurityUserService {

    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";

    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;

    @Autowired
    private DefaultTokenServices defaultTokenServices;

    @Override
    public Map<String, OAuth2AccessToken> validatePasswordResetToken(long id, String token) {

        Map<String, OAuth2AccessToken> resultMap = new HashMap<>();
//            Pair<String, OAuth2AccessToken> pair; = new Pair<>();

        final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);
        if ((passToken == null) || (passToken.getUser().getId() != id)) {
            resultMap.put(TOKEN_INVALID, null);
            return resultMap;
        }

        final Calendar cal = Calendar.getInstance();
        if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            passwordTokenRepository.delete(passToken);
            resultMap.put(TOKEN_EXPIRED, null);
            return resultMap;
        }

        final User user = passToken.getUser();
//        passwordTokenRepository.delete(passToken);


        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE"));

        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("username", user.getLogin());
        requestParameters.put("password", "");

        boolean approved = true;

        Set<String> scope = new HashSet<>();
        scope.add("global");

        Set<String> resourceIds = new HashSet<>();
        resourceIds.add("oauth2-resource");

        Set<String> responseTypes = new HashSet<>();
        responseTypes.add("access_token");

        Map<String, Serializable> extensionProperties = new HashMap<>();

        OAuth2Request oAuth2Request = new OAuth2Request(requestParameters, null, authorities,
                approved, scope, resourceIds, null, responseTypes, extensionProperties);

        org.springframework.security.core.userdetails.User userPrincipal = new org.springframework.security.core.userdetails.User(requestParameters.get("username"), requestParameters.get("password"), true, true, true, true, authorities);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userPrincipal, null, authorities);
        OAuth2Authentication auths = new OAuth2Authentication(oAuth2Request, authenticationToken);
        OAuth2AccessToken accessToken = defaultTokenServices.createAccessToken(auths);

        resultMap.put(TOKEN_VALID, accessToken);
        return resultMap;

    }
}
