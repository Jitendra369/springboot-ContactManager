package com.springb.contactmanager.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springb.contactmanager.entity.User;

// handle database operations 
public interface UserRepository extends JpaRepository<User, Integer>  {

    @Query("select u from User u where u.userEmail =:userEmail")
    public User getUserByUserName(@Param("userEmail") String email);

    
}
