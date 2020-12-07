package com.twitter.service;

import com.twitter.dto.TweetDTO;
import com.twitter.dto.TweetListDTO;
import com.twitter.exception.TweetCreationException;
import com.twitter.record.UserTweetRecord;

import reactor.core.publisher.Mono;

/**
 * Service responsible for processing tweets related operations
 */
public interface TweetService {

	/**
	 * Method used to create tweet basing on user id and {@link TweetDTO}, exception of type
	 * {@link TweetCreationException} will be thrown in case of exception during process
	 * 
	 * @param userId
	 *            user id
	 * @param tweetDTO
	 *            tweet dto from request body
	 * @return record with user id and tweet id
	 */
	Mono<UserTweetRecord> createTweetForUser(String userId, TweetDTO tweetDTO);

	/**
	 * Method used to retrieve tweet basing on given id
	 * 
	 * @param tweetId
	 *            tweet id
	 * @return DTO with tweet
	 */
	Mono<TweetDTO> retrieveTweetById(String tweetId);

	/**
	 * Method used to retrieve all tweets for given user
	 * 
	 * @param userId
	 *            user id
	 * @return list with tweets DTOs
	 */
	Mono<TweetListDTO> retrieveTweetsFromUser(String userId);

	/**
	 * Method used to retrieve all tweets for all followed user
	 * 
	 * @param userId
	 *            user id
	 * @return list with tweets DTOs
	 */
	Mono<TweetListDTO> retrieveTweetsForFollowedUsers(String userId);
}
