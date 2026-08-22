package com.nitesh.unique.services;
import com.nitesh.unique.entity.UserEntry;
import com.nitesh.unique.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class UserService {


    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveNewUser(UserEntry user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(List.of("USER"));
            userRepo.save(user);
        }
        catch (Exception e){
            log.error("error occured for {}", user.getUserName(),e);
        }

    }

    public void saveUser(UserEntry user) {
        userRepo.save(user);
    }

    public List<UserEntry> getAllUsers() {
        return userRepo.findAll();
    }

    public Optional<UserEntry> findUserById(ObjectId id) {
        return userRepo.findById(id);
    }

    public void deleteUserById(ObjectId id) {
        userRepo.deleteById(id);
    }

    public UserEntry findByUserName(String userName) {
        return userRepo.findByUserName(userName);
    }

    public void saveAdminUser(UserEntry user) {
        user.setRoles(List.of("USER", "ADMIN"));
        userRepo.save(user);
    }
}


