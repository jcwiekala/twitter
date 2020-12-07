package com.twitter.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.twitter.dto.FollowUserDTO;
import com.twitter.dto.UserDetailsDTO;
import com.twitter.service.UserService;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Controller used for handling operations related to users
 */
@RestController
@RequestMapping("/v1/users")
@AllArgsConstructor
public class UserController extends AbstractController {

	private final UserService userService;

	@PostMapping
	public Mono<ResponseEntity<Object>> createUser(@RequestBody @Valid final UserDetailsDTO userDetailsDTO) {
		return userService //
		                .createUserRetrieveAndConvert(userDetailsDTO) //
		                .map(processedUserDetailsDto -> ResponseEntity //
		                                .created(URI.create("/v1/users/" + processedUserDetailsDto.getId())) //
		                                .body(processedUserDetailsDto));
	}

	@GetMapping("/{userId}")
	public Mono<ResponseEntity<UserDetailsDTO>> retrieveUserDetails(@PathVariable final String userId) {
		return userService //
		                .retrieveUserAndConvert(userId) //
		                .map(ResponseEntity::ok);
	}

	@PostMapping("/{currentUserId}/follow")
	public Mono<ResponseEntity<UserDetailsDTO>> followUser(@PathVariable final String currentUserId,
	                @RequestBody @Valid final FollowUserDTO followUserDTO) {
		return userService //
		                .followUserRetrieveAndConvert(currentUserId, followUserDTO.getUserId()) //
		                .map(ResponseEntity::ok);
	}
}