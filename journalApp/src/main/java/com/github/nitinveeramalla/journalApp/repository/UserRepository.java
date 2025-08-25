package com.github.nitinveeramalla.journalApp.repository;

import com.github.nitinveeramalla.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUserName(String UserName);

    void deleteByUserName(String userName);
}
