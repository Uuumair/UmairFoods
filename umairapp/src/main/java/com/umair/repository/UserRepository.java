package com.umair.repository;
import com.umair.model.*;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User ,Long> {

    public User findByEmail(String email);
    

}
