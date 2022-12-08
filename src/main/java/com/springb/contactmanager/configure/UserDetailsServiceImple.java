package com.springb.contactmanager.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.springb.contactmanager.dao.UserRepository;
import com.springb.contactmanager.entity.User;

/**
 * UserDetailsServiceImple
 */

/**
* this class load the content from the database, and update the bean of UserDetails
    UserDetalas has the method, getUserName & getPassword through which it can get userinformation 
* 
*/

public class UserDetailsServiceImple implements UserDetailsService{

    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        get the user from repository 
    	User user = this.repository.getUserByUserName(username);
        if (user==null) {
            throw new UsernameNotFoundException("could not found user");
        }

        CustomeUserDetails customeUserDetails = new CustomeUserDetails(user);

        return customeUserDetails;
    }

    
}