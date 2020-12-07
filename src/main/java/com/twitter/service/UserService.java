package com.twitter.service;

import com.twitter.domain.User;
import com.twitter.dto.UserDetailsDTO;
import com.twitter.exception.UserNotFoundException;

import reactor.core.publisher.Mono;

/**
 * Service responsible for processing tweets related operations
 */
public interface UserService {

	/**
	 * Method used to create user basing on fields from {@link UserDetailsDTO}: id, firstName, LastName. Then
	 * {@link User} is retrieved and converted back to {@link UserDetailsDTO}.
	 * 
	 * @param user
	 *            user dto containing the required data to create new {@link User}
	 * @return {@link Mono} with user document converted to {@link UserDetailsDTO}
	 */
	Mono<UserDetailsDTO> createUserRetrieveAndConvert(UserDetailsDTO user);

	/**
	 * Method used to retrieve user by id if exists and convert to {@link UserDetailsDTO}. Method throws
	 * {@link UserNotFoundException} if user could not be found
	 * 
	 * @param id
	 *            user id which should be retrieved
	 * @return {@link Mono} with user document converted to {@link UserDetailsDTO}
	 */
	Mono<UserDetailsDTO> retrieveUserAndConvert(String id);

	/**
	 * Method used to update {@link User} identified by updating set of followed users - identifier of user to follow
	 * will be added to set if it could be found in database. At the end of processing user is converted to
	 * {@link UserDetailsDTO}. Method throws {@link UserNotFoundException} if users could not be found
	 * 
	 * @param currentUserId
	 *            user id which will be updated
	 * @param userIdToFollow
	 *            user id which indicated to the user which should be followed
	 * @return {@link Mono} with user document converted to {@link UserDetailsDTO}
	 */
	Mono<UserDetailsDTO> followUserRetrieveAndConvert(String currentUserId, String userIdToFollow);

	/**
	 * Method used to retrieve {@link User} by given user id, throws {@link UserNotFoundException} in case if user
	 * document could not be found in database
	 * 
	 * @param id
	 *            user id
	 * @return {@link Mono} with user document
	 */
	Mono<User> retrieveUserById(String id);

	/**
	 * Method used to update given user in database
	 * 
	 * @param user
	 *            user document
	 * @return {@link Mono} with user document
	 */
	Mono<User> updateUser(User user);
}
