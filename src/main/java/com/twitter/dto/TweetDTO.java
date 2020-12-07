package com.twitter.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO responsible for returning tweet data with author id in some cases
 */
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TweetDTO {

	@Size(max = 140)
	@NotBlank
	private String text;
	private String id;
	private Date creationTime;
	private String author;
}
