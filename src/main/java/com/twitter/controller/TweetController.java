package com.twitter.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.twitter.dto.TweetDTO;
import com.twitter.dto.TweetListDTO;
import com.twitter.record.UserTweetRecord;
import com.twitter.service.TweetService;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Controller used for handling operations related to tweets
 */
@RestController
@RequestMapping("/v1/users/{userId}/tweets")
@AllArgsConstructor
public class TweetController extends AbstractController {

	private TweetService tweetService;

	@PostMapping
	public Mono<ResponseEntity<TweetDTO>> createTweetForUser(@PathVariable final String userId,
	                @RequestBody @Valid final TweetDTO tweetDTO) {
		return tweetService //
		                .createTweetForUser(userId, tweetDTO) //
		                .map(userTweetRecord -> ResponseEntity.created(createTweetLocationURI(userTweetRecord))
		                                .body(userTweetRecord.tweetDTO()));
	}

	@GetMapping
	public Mono<ResponseEntity<TweetListDTO>> retrieveTweetsForUser(@PathVariable final String userId) {
		return tweetService //
		                .retrieveTweetsFromUser(userId) //
		                .map(ResponseEntity::ok);
	}

	@GetMapping("/followed")
	public Mono<ResponseEntity<TweetListDTO>> retrieveTweetsForFollowedUsers(@PathVariable final String userId) {
		return tweetService //
		                .retrieveTweetsForFollowedUsers(userId) //
		                .map(ResponseEntity::ok);
	}

	@GetMapping("/{tweetId}")
	public Mono<ResponseEntity<TweetDTO>> retrieveTweetById(@PathVariable final String tweetId) {
		return tweetService //
		                .retrieveTweetById(tweetId) //
		                .map(ResponseEntity::ok);
	}

	private URI createTweetLocationURI(final UserTweetRecord userTweetRecord) {
		return URI.create(String.format("/v1/users/%s/tweets/%s", userTweetRecord.userId(),
		                userTweetRecord.tweetDTO().getId()));
	}
}
