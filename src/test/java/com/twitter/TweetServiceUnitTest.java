package com.twitter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.twitter.domain.Tweet;
import com.twitter.domain.User;
import com.twitter.dto.TweetDTO;
import com.twitter.record.UserTweetRecord;
import com.twitter.repository.TweetRepository;
import com.twitter.service.impl.TweetServiceImpl;
import com.twitter.service.impl.UserServiceImpl;

import reactor.core.publisher.Mono;

class TweetServiceUnitTest {

	private static TweetServiceImpl tweetService;

	private static final String TEST_TEXT = "testText";
	private static final String TEST_USER_ID = "testUserId";
	private static final String TEST_USER_ID_NOT_EXISTS = "testUserIdNotExists";
	private static final String TEST_FOLLOWED_USER_ID = "testFollowedUserId";
	private static final String TEST_NAME = "testName";
	private static final String TEST_LASTNAME = "testLastname";

	private static final TweetDTO TWEET_DTO_TEXT_ONLY = new TweetDTO(TEST_TEXT, null, null, null);
	private static final Tweet TEST_TWEET = new Tweet(TEST_TEXT);

	private static final User TEST_USER = new User(TEST_USER_ID, TEST_NAME, TEST_LASTNAME);
	private static final User TEST_FOLLOWED_USER = new User(TEST_FOLLOWED_USER_ID, TEST_NAME, TEST_LASTNAME);

	@BeforeAll
	public static void setup() {
		UserServiceImpl userService = mock(UserServiceImpl.class);
		TweetRepository tweetRepository = mock(TweetRepository.class);

		when(userService.retrieveUserById(TEST_USER_ID)).thenReturn(Mono.just(TEST_USER));
		when(userService.updateUser(any())).thenReturn(Mono.just(TEST_USER));
		when(tweetRepository.insert(any(Tweet.class))).thenReturn(Mono.just(TEST_TWEET));
		// when(tweetRepository.findById(((String)any())).thenReturn(Mono.just(TEST_TWEET)));

		tweetService = new TweetServiceImpl(userService, tweetRepository);
	}

	@Test
	void createTweetForUser() {
		final UserTweetRecord returnedUserTweetRecord = tweetService
		                .createTweetForUser(TEST_USER_ID, TWEET_DTO_TEXT_ONLY).block();

		assertEquals(TEST_USER_ID, returnedUserTweetRecord.userId());
		assertNotNull(returnedUserTweetRecord.tweetDTO());
		assertNotNull(returnedUserTweetRecord.tweetDTO().getId());

	}

	void retrieveTweetsFromUser() {
		// TODO
	}

	void retrieveTweetsForFollowedUsers() {
		// TODO
	}
}
