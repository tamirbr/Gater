package online.gater.config.security;

import org.springframework.security.core.Authentication;

import online.gater.model.User;

public class LoggedInUser {
	
	public static User getUser(Authentication authentication) {
		User user = null;
		try {
			user = (User) authentication.getPrincipal();
			return user;
		} catch(Exception e) {
			return null;
		}
	}
}
