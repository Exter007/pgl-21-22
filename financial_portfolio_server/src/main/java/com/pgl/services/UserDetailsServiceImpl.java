package com.pgl.services;

import com.pgl.models.User;
import com.pgl.security.UserDetailsImpl;
import com.pgl.utils.ContextName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    public UserService userService;

    @Autowired
    ContextName context;

    public UserDetailsServiceImpl(ContextName contextName) {
        this.context = contextName;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByLogin(username, context.name());
        if(user == null) throw new UsernameNotFoundException("User Not Found with username: " + username);

        return UserDetailsImpl.build(user);
    }
}
