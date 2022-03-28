package com.pgl.services;

import com.pgl.models.User;
import com.pgl.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service()
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    public UserService userService;

    public UserDetailsServiceImpl() {
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userService.findByLogin(login);
        if(user == null) throw new UsernameNotFoundException("User Not Found with login: " + login);

        return UserDetailsImpl.build(user);
    }
}
