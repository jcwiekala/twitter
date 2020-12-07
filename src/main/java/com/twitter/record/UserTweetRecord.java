package com.twitter.record;

import com.twitter.dto.TweetDTO;

/**
 * Record used to group user if with tweet dto
 */
public record UserTweetRecord(String userId, TweetDTO tweetDTO) {
}
