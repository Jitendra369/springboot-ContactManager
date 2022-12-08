package com.springb.contactmanager.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springb.contactmanager.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer>{

    // get All the contact with userid
//    @Query("from Contact as c where c.user.userID =:userID ")
//	public List<Contact> findContactByUserId(@Param("userID") int userID);
	
	@Query("from Contact as c where c.user.userID =:userID ")
	public Page<Contact> findContactByUserId(@Param("userID") int userID, Pageable pageable);
    
    
}
