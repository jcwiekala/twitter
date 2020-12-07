package com.twitter.dto;

import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO with user details informations
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDetailsDTO {

	@NotBlank
	private String id;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	private Set<String> followedUsers;
}
