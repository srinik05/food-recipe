package com.assignment.recipe.service;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * RecipeApplcation - UserService for Authenication passsing In-Memory username and password
 * @author Srinviasu Kaki 
 * @version 1.0
 *
 */
@Service
public class UserService implements UserDetailsService {

	/**
	 * Load User by Username 
	 * @param username 
	 * @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		if(!userName.equals("admin")){
			throw new UsernameNotFoundException(userName + " not found");
		}

		return new User("admin","password",new ArrayList<>());
	}
}
