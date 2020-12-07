package com.twitter.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO with list of {@link TweetDTO}
 */
@Data
@AllArgsConstructor
public class TweetListDTO {

	private List<TweetDTO> tweets;
}
