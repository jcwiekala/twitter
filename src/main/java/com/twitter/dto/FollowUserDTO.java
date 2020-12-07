package com.twitter.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * DTO handling response data for user following process
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FollowUserDTO {

	private String userId;
}
