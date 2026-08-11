package com.nitesh.unique.services;
import com.nitesh.unique.entity.UserEntry;
import com.nitesh.unique.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveNewUser(UserEntry user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER"));
        userRepo.save(user);
    }

    public void saveUser(UserEntry user){
        userRepo.save(user);
    }

    public List<UserEntry> getAllUsers(){
        return userRepo.findAll();
    }

    public Optional<UserEntry> findUserById( ObjectId id){
       return userRepo.findById(id);
    }
    public  void deleteUserById( ObjectId id){
        userRepo.deleteById(id);
    }

    public UserEntry findByUserName(String userName){
      return  userRepo.findByUserName(userName);
    }
}
