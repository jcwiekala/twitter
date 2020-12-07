package com.twitter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO handling exception message
 */
@Data
@AllArgsConstructor
public class ExceptionDTO {
	private String message;
}
