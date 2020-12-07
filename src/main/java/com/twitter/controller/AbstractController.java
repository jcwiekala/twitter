package com.twitter.controller;

import com.twitter.exception.TweetNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.twitter.dto.ExceptionDTO;
import com.twitter.exception.TweetCreationException;
import com.twitter.exception.UserNotFoundException;

/**
 * Abstract controller handling common exceptions and validation errors
 */
public abstract class AbstractController {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionDTO> handleBindingException(
	                final MethodArgumentNotValidException methodArgumentNotValidException) {

		final BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
		final FieldError fieldError = bindingResult.getFieldError();

		if (bindingResult.hasFieldErrors() && fieldError != null) {
			return ResponseEntity.badRequest()
			                .body(new ExceptionDTO(fieldError.getField() + " " + fieldError.getDefaultMessage()));
		}

		return ResponseEntity.badRequest().build();
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public ResponseEntity<ExceptionDTO> handleDuplicateUserException() {
		return ResponseEntity.badRequest().body(new ExceptionDTO("Item already exists"));
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ExceptionDTO> handleUserNotFoundException(final UserNotFoundException userNotFoundException) {
		return ResponseEntity.badRequest().body(new ExceptionDTO(userNotFoundException.getMessage()));
	}

	@ExceptionHandler(TweetCreationException.class)
	public ResponseEntity handleTweetCreationException(final TweetCreationException exception) {
		return ResponseEntity.badRequest().body(new ExceptionDTO(exception.getMessage()));
	}

	@ExceptionHandler(TweetNotFoundException.class)
	public ResponseEntity handleTweetCreationException(final TweetNotFoundException exception) {
		return ResponseEntity.badRequest().body(new ExceptionDTO(exception.getMessage()));
	}
}
