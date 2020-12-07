package com.twitter.exception;

/**
 * Exception thrown if there was an error during tweet creation process
 */
public class TweetCreationException extends RuntimeException {

	public TweetCreationException(
	                              final String message) {
		super(message);
	}
}
