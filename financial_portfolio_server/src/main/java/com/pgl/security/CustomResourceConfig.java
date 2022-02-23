package com.pgl.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class CustomResourceConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception{
//        http.addFilterAfter(new AuthenticationFilter(), AuthenticationFilter.class)
//    }
//    public CustomResourceConfig() {
//        packages("com.pgl.utils.security");
//        register(AuthenticationFilter.class);
    }

}