package com.twitter.helper;

import com.twitter.domain.User;
import com.twitter.dto.UserDetailsDTO;

/**
 * Helper used for conversion from {@link User} to {@link UserDetailsDTO}
 */
public class UserConversionHelper {

	private UserConversionHelper() {
		// Empty to prevent instantiation
	}

	/**
	 * Method used to convert {@link User} to {@link UserDetailsDTO}
	 * 
	 * @param user
	 *            user document
	 * @return converted dto
	 */
	public static UserDetailsDTO convertUserToDTO(final User user) {
		return new UserDetailsDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getFollowedUsersIds());
	}
}
