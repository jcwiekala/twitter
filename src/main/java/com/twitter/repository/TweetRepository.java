package com.twitter.repository;

import java.util.Collection;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.twitter.domain.Tweet;

import reactor.core.publisher.Flux;

/**
 * Reactive repository working on {@link Tweet} documents
 */
@Repository
public interface TweetRepository extends ReactiveMongoRepository<Tweet, String> {

	/**
	 * Method used to return all {@link Tweet} documents for given tweets ids
	 * 
	 * @param tweetIds
	 *            tweets ids
	 * @return flux with found {@link Tweet} documents
	 */
	Flux<Tweet> findAllByIdIn(Collection<String> tweetIds);

	/**
	 * Method used to return all {@link Tweet} documents for given tweets ids, ordering by creation time descending
	 * 
	 * @param tweetIds
	 *            tweets ids
	 * @return flux with found {@link Tweet} documents
	 */
	Flux<Tweet> findAllByIdInOrderByCreationTimeDesc(Collection<String> tweetIds);
}
