package com.twitter.domain;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Tweet document
 */
@Data
@Document(collection = "tweets")
@RequiredArgsConstructor
public class Tweet {

	@Id
	@Indexed
	private String id = UUID.randomUUID().toString();

	@NonNull
	private String text;

	private Date creationTime = new Date();
}
