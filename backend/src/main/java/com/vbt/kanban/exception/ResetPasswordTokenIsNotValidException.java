package com.vbt.kanban.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ResetPasswordTokenIsNotValidException extends Exception {

	private static final long serialVersionUID = 1L;

	public ResetPasswordTokenIsNotValidException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResetPasswordTokenIsNotValidException(String message) {
		super(message);
	}

}
