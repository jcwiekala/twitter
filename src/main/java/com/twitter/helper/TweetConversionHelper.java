package com.twitter.helper;

import com.twitter.domain.Tweet;
import com.twitter.dto.TweetDTO;

/**
 * Helper used for conversion from {@link Tweet} to {@link TweetDTO}
 */
public class TweetConversionHelper {

	private TweetConversionHelper() {
		// Empty to prevent instantiation
	}

	/**
	 * Method used to convert {@link Tweet} to {@link TweetDTO}
	 * 
	 * @param tweet
	 *            tweet document
	 * @return converted dto
	 */
	public static TweetDTO convertTweetToDTO(final Tweet tweet) {
		return new TweetDTO(tweet.getText(), tweet.getId(), tweet.getCreationTime(), null);
	}

	/**
	 * Method used to convert {@link Tweet} to {@link TweetDTO}, additionally setting tweet's author id on dto
	 * 
	 * @param userId
	 *            tweet author id
	 * @param tweet
	 *            tweet document
	 * @return converted dto
	 */
	public static TweetDTO convertTweetWithAuthorToDTO(final String userId, final Tweet tweet) {
		return new TweetDTO(tweet.getText(), tweet.getId(), tweet.getCreationTime(), userId);

	}
}
