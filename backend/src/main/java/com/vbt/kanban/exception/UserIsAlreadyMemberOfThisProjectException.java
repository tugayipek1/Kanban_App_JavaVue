package com.vbt.kanban.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserIsAlreadyMemberOfThisProjectException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public UserIsAlreadyMemberOfThisProjectException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserIsAlreadyMemberOfThisProjectException(String message) {
		super(message);
	}
}
