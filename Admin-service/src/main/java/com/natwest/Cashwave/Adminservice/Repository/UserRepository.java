package com.natwest.Cashwave.Adminservice.Repository;

import com.natwest.Cashwave.Adminservice.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
    User findByaccountNumber(String accountNumber);
}
