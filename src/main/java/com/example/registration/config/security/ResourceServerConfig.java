package com.example.registration.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
//        http.anonymous().disable().authorizeRequests().anyRequest().authenticated();
        http
                .authorizeRequests()
                .antMatchers("/user/savePassword*").hasAuthority("CHANGE_PASSWORD_PRIVILEGE")
                .antMatchers("/chatApi/**").hasRole("ADMIN")
                .anyRequest().permitAll();
    }
}