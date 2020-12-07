package com.twitter.exception;

/**
 * Exception thrown if tweet could not be found
 */
public class TweetNotFoundException extends RuntimeException {
	public TweetNotFoundException(
	                              final String tweetId) {
		super(String.format("Tweet %s was not found", tweetId));
	}
}
