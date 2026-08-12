package com.nitesh.unique.services;

import com.nitesh.unique.entity.UserEntry;
import com.nitesh.unique.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDetailServiceImplementation implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        System.out.println("USERNAME FROM BASIC AUTH: " + userName);
        UserEntry user =  userRepo.findByUserName(userName);
        if(user!=null) {
            System.out.println("USER FOUND IN DATABASE: " + user.getUserName());

           return User.builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
        }
        throw new UsernameNotFoundException("Username not found with username" +userName);


    }
}
