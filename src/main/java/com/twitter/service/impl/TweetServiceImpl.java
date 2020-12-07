package com.twitter.service.impl;

import java.util.Comparator;

import org.springframework.stereotype.Service;

import com.twitter.domain.Tweet;
import com.twitter.domain.User;
import com.twitter.dto.TweetDTO;
import com.twitter.dto.TweetListDTO;
import com.twitter.exception.TweetCreationException;
import com.twitter.exception.TweetNotFoundException;
import com.twitter.helper.TweetConversionHelper;
import com.twitter.record.UserTweetRecord;
import com.twitter.repository.TweetRepository;
import com.twitter.service.TweetService;
import com.twitter.service.UserService;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Default implementation of {@link TweetService}
 */
@Service
@AllArgsConstructor
public class TweetServiceImpl implements TweetService {
	private UserService userService;
	private TweetRepository tweetRepository;

	@Override
	public Mono<UserTweetRecord> createTweetForUser(final String userId, final TweetDTO tweetDTO) {
		return userService //
		                .retrieveUserById(userId) //
		                .flatMap(user -> createTweetAndUpdateUser(tweetDTO, user)) //
		                .switchIfEmpty(Mono.error(() -> new TweetCreationException(
		                                "Tweet could not be created for user " + userId)));
	}

	@Override
	public Mono<TweetDTO> retrieveTweetById(final String tweetId) {
		return tweetRepository //
		                .findById(tweetId) //
		                .switchIfEmpty(Mono.error(() -> new TweetNotFoundException(tweetId))) //
		                .map(TweetConversionHelper::convertTweetToDTO);
	}

	@Override
	public Mono<TweetListDTO> retrieveTweetsFromUser(final String userId) {
		return userService //
		                .retrieveUserById(userId) //
		                .map(User::getTweetsIds) //
		                .flatMapMany(tweetRepository::findAllByIdInOrderByCreationTimeDesc) //
		                .map(TweetConversionHelper::convertTweetToDTO) //
		                .collectList() //
		                .map(TweetListDTO::new);
	}

	@Override
	public Mono<TweetListDTO> retrieveTweetsForFollowedUsers(final String userId) {
		return userService //
		                .retrieveUserById(userId) //
		                .flatMapIterable(User::getFollowedUsersIds) //
		                .flatMap(userService::retrieveUserById) //
		                .flatMap(this::findAllTweetsAndConvert) //
		                .sort(Comparator.comparing(TweetDTO::getCreationTime).reversed()) //
		                .collectList() //
		                .map(TweetListDTO::new);
	}

	private Mono<UserTweetRecord> createTweetAndUpdateUser(final TweetDTO tweetDTO, final User user) {
		return tweetRepository //
		                .insert(new Tweet(tweetDTO.getText())) //
		                .flatMap(tweet -> updateUserWithTweetId(user, tweet));
	}

	private Mono<UserTweetRecord> updateUserWithTweetId(final User user, final Tweet tweet) {
		user.addTweet(tweet.getId());
		return userService //
		                .updateUser(user) //
		                .map(updatedUser -> new UserTweetRecord(updatedUser.getId(),
		                                TweetConversionHelper.convertTweetToDTO(tweet)));
	}

	private Flux<TweetDTO> findAllTweetsAndConvert(final User user) {
		return tweetRepository //
		                .findAllByIdIn(user.getTweetsIds()) //
		                .map(tweet -> TweetConversionHelper.convertTweetWithAuthorToDTO(user.getId(), tweet));
	}

}
