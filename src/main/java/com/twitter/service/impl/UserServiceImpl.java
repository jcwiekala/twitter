package com.twitter.service.impl;

import org.springframework.stereotype.Service;

import com.twitter.domain.User;
import com.twitter.dto.UserDetailsDTO;
import com.twitter.exception.UserNotFoundException;
import com.twitter.helper.UserConversionHelper;
import com.twitter.repository.UserRepository;
import com.twitter.service.UserService;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Default implementation of {@link UserService}
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	@Override
	public Mono<UserDetailsDTO> createUserRetrieveAndConvert(final UserDetailsDTO user) {
		return retrieveUserById(user.getId()) //
		                .onErrorReturn(new User(user.getId(), user.getFirstName(), user.getLastName())) //
		                .flatMap(userRepository::insert) //
		                .map(UserConversionHelper::convertUserToDTO);
	}

	@Override
	public Mono<UserDetailsDTO> followUserRetrieveAndConvert(final String currentUserId, final String userIdToFollow) {
		return retrieveUserById(currentUserId) //
		                .flatMap(currentUser -> processUserFollowUpdate(userIdToFollow, currentUser)) //
		                .map(UserConversionHelper::convertUserToDTO);
	}

	@Override
	public Mono<User> retrieveUserById(final String id) {
		return userRepository //
		                .findById(id) //
		                .switchIfEmpty(Mono.error(() -> new UserNotFoundException(id)));
	}

	@Override
	public Mono<User> updateUser(final User user) {
		return userRepository.save(user);
	}

	@Override
	public Mono<UserDetailsDTO> retrieveUserAndConvert(final String id) {
		return retrieveUserById(id) //
		                .map(UserConversionHelper::convertUserToDTO);
	}

	private Mono<User> processUserFollowUpdate(final String userIdToFollow, final User currentUser) {
		return retrieveUserById(userIdToFollow) //
		                .flatMap(userToFollow -> updateUserWithFollowedUserId(currentUser, userToFollow));
	}

	private Mono<User> updateUserWithFollowedUserId(final User currentUser, final User userToFollow) {
		currentUser.addFollowedUser(userToFollow.getId());
		return updateUser(currentUser);
	}
}
