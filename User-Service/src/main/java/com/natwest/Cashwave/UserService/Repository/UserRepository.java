package com.natwest.Cashwave.UserService.Repository;

import com.natwest.Cashwave.UserService.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
}
