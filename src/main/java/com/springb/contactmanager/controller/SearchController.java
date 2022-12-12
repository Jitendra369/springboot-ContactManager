package com.springb.contactmanager.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.springb.contactmanager.dao.ContactRepository;
import com.springb.contactmanager.dao.UserRepository;
import com.springb.contactmanager.entity.Contact;
import com.springb.contactmanager.entity.User;

@RestController
public class SearchController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
	
	@GetMapping("/search/{query}")
	public ResponseEntity<?> search(@PathVariable("query") String query,Principal principal){
		String currUser= principal.getName();
	    User user = this.userRepository.getUserByUserName(currUser);
		
	   List<Contact> list  = this.contactRepository.findByContNameContainingAndUser(query, user);
		
		return ResponseEntity.ok(list);
	}
}
