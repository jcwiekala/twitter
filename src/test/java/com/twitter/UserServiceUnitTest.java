package com.twitter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;

import com.twitter.domain.User;
import com.twitter.dto.UserDetailsDTO;
import com.twitter.exception.UserNotFoundException;
import com.twitter.repository.UserRepository;
import com.twitter.service.impl.UserServiceImpl;

import reactor.core.publisher.Mono;

/**
 * Class providing unit tests for public methods from {@link UserServiceImpl}
 */
class UserServiceUnitTest {

	private static final String TEST_USER_ID = "testUserId";
	private static final String TEST_USER_ID_NOT_EXISTS = "testUserIdNotExists";
	private static final String TEST_FOLLOWED_USER_ID = "testFollowedUserId";
	private static final String TEST_NAME = "testName";
	private static final String TEST_LASTNAME = "testLastname";

	private static final User TEST_USER = new User(TEST_USER_ID, TEST_NAME, TEST_LASTNAME);
	private static final User TEST_FOLLOWED_USER = new User(TEST_FOLLOWED_USER_ID, TEST_NAME, TEST_LASTNAME);

	private static final UserDetailsDTO testUserDetailsDTO = new UserDetailsDTO(TEST_USER_ID, TEST_NAME, TEST_LASTNAME,
	                null);

	private static UserServiceImpl userServiceImpl;

	@BeforeAll
	public static void setup() {
		UserRepository userRepository = mock(UserRepository.class);

		when(userRepository.findById(TEST_USER_ID)).thenReturn(Mono.just(TEST_USER));
		when(userRepository.findById(TEST_FOLLOWED_USER_ID)).thenReturn(Mono.just(TEST_FOLLOWED_USER));
		when(userRepository.findById(TEST_USER_ID_NOT_EXISTS)).thenReturn(Mono.empty());
		when(userRepository.save(TEST_USER)).thenReturn(Mono.just(TEST_USER));
		when(userRepository.insert(TEST_USER)).thenReturn(Mono.just(TEST_USER));

		userServiceImpl = new UserServiceImpl(userRepository);
	}

	@Test
	void createUserRetrieveAndConvert() {
		final UserDetailsDTO returnedUserDetailsDTO = userServiceImpl.createUserRetrieveAndConvert(testUserDetailsDTO)
		                .block();

		assertEquals(TEST_USER_ID, returnedUserDetailsDTO.getId());
		assertEquals(TEST_NAME, returnedUserDetailsDTO.getFirstName());
		assertEquals(TEST_LASTNAME, returnedUserDetailsDTO.getLastName());
	}

	@Test
	void followUserRetrieveAndConvert() {
		final UserDetailsDTO returnedUserDetailsDTO = userServiceImpl
		                .followUserRetrieveAndConvert(TEST_USER_ID, TEST_FOLLOWED_USER_ID).block();

		final Set<String> followedUsers = returnedUserDetailsDTO.getFollowedUsers();

		assertEquals(TEST_USER_ID, returnedUserDetailsDTO.getId());
		assertFalse(CollectionUtils.isEmpty(followedUsers));
		assertEquals(TEST_FOLLOWED_USER_ID, followedUsers.iterator().next());
	}

	@Test
	void retrieveUserById() {
		final User returnedUser = userServiceImpl.retrieveUserById(TEST_USER_ID).block();

		assertEquals(TEST_USER_ID, returnedUser.getId());
	}

	@Test
	void retrieveUserByIdWithException() {
		Assertions.assertThrows(UserNotFoundException.class,
		                () -> userServiceImpl.retrieveUserById(TEST_USER_ID_NOT_EXISTS).block());
	}

	@Test
	void updateUser() {
		final User returnedUser = userServiceImpl.updateUser(TEST_USER).block();

		assertEquals(TEST_USER_ID, returnedUser.getId());
		assertEquals(TEST_NAME, returnedUser.getFirstName());
		assertEquals(TEST_LASTNAME, returnedUser.getLastName());
	}

	@Test
	void retrieveUserAndConvert() {
		final UserDetailsDTO returnedUserDetailsDTO = userServiceImpl.retrieveUserAndConvert(TEST_USER_ID).block();

		assertEquals(TEST_USER_ID, returnedUserDetailsDTO.getId());
		assertEquals(TEST_NAME, returnedUserDetailsDTO.getFirstName());
		assertEquals(TEST_LASTNAME, returnedUserDetailsDTO.getLastName());
	}
}
