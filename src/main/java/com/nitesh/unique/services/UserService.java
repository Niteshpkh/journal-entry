package com.nitesh.unique.services;
import com.nitesh.unique.entity.UserEntry;
import com.nitesh.unique.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public void saveUser(UserEntry user){
        userRepo.save(user);
    }

    public List<UserEntry> getAllUsers(){
        return userRepo.findAll();
    }

    public Optional<UserEntry> findUserById(@PathVariable ObjectId id){
       return userRepo.findById(id);
    }
    public  void deleteUserById(@PathVariable ObjectId id){
        userRepo.deleteById(id);
    }

    public UserEntry findByUserName(String username){
      return  userRepo.findByUserName(username);
    }
}
