package com.twitter.exception;

/**
 * Exception thrown if user could not be found
 */
public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(
	                             final String userId) {
		super(String.format("User %s was not found", userId));
	}
}
