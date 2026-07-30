package com.nitesh.unique.repository;

import com.nitesh.unique.entity.UserEntry;
import org.bson.types.ObjectId;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntry, ObjectId> {
    UserEntry findByUserName(String username);
}
