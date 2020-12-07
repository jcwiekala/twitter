package com.twitter.domain;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * User document
 */
@Data
@RequiredArgsConstructor
@Document(collection = "users")
public class User {

	@Id
	@Indexed
	@NonNull
	private String id;

	@NonNull
	private String firstName;

	@NonNull
	private String lastName;

	private Set<String> followedUsersIds = new HashSet<>();
	private Set<String> tweetsIds = new HashSet<>();

	public void addTweet(final String tweetId) {
		tweetsIds.add(tweetId);

	}

	public void addFollowedUser(final String userId) {
		followedUsersIds.add(userId);
	}
}
