package com.vbt.kanban.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResetPasswordRequestNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public ResetPasswordRequestNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResetPasswordRequestNotFoundException(String message) {
		super(message);
	}

}
